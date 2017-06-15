import java.io.*;
import java.util.*;

import it.unitn.disi.smatch.data.mappings.IContextMapping;
import it.unitn.disi.smatch.data.mappings.IMappingElement;
import it.unitn.disi.smatch.data.trees.INode;

/*
 * This class is responsible for initially taking in both the 
 * target and source schema before calling SPSM with these schemas
 * it will then store the results as an ArrayList of Match_Struc objects
 * 
 */

public class Call_SPSM{
	
	private String[] targetList;
	
	//main method used for testing purposes during dev only
	public static void main (String[] args){
		Call_SPSM classInst = new Call_SPSM();
		
		ArrayList<Match_Struc> result = new ArrayList<Match_Struc>();
		classInst.getSchemas(result,null,null);
		
		//then lets see if we can read the results from our new structure
		//and print them out to the console for the user
		if(result.size() == 0){
			System.out.println("No Matches.");
		}else{
			for(int i = 0 ; i < result.size() ; i++){
				Match_Struc currMatch = result.get(i);
				System.out.println("");
				System.out.println("Result Number "+(i+1)+": "+currMatch.getDatasetSchema());
				System.out.println("Has a similarity of "+currMatch.getSimValue());
				System.out.println("And " + currMatch.getNumMatches() + " matche(s)");

				if(currMatch.getNumMatches() > 0){
					System.out.println("These matches are: ");

					ArrayList<String[]> indivMatches = currMatch.getMatches();
					for(int j = 0 ; j < indivMatches.size() ; j++){
						String[] currIndivMatch = indivMatches.get(j);
						System.out.println(currIndivMatch[0] + "," + currIndivMatch[1] + "," + currIndivMatch[2]);
					}
				}
			}
		}	
	}
	
	//get the schemas from the user, either through the console or as a parameter
	//when this method gets called
	public void getSchemas(ArrayList<Match_Struc> results, String srcSchema, String targetSchema){

		//if we haven't been passed the schemas as params
		//then get them through the command line
		if(srcSchema==null && targetSchema==null){
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			
			try{
				//get source schema through command line
				System.out.println("Please enter source schema: ");
				while((srcSchema=reader.readLine()).equals("")){
				}
				
				//then get target schema(s)
				//separated by a ; for now for easy manipulation
				System.out.println("Please enter target schema(s) seperated by a ';' : ");
				while((targetSchema=reader.readLine()).equals("")){
				}
				
				reader.close();
				
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		try{
			//then write the source schema to file
			PrintWriter srcWriter = new PrintWriter("inputs/source.txt","UTF-8");
			srcWriter.write(srcSchema);
			srcWriter.close();
			
			//then save the target schemas to our array
			//by splitting string at ';'
			targetList = targetSchema.split(";");
			
			//then for each of the target schemas
			//we want to call SPSM and store the results
			String currTarget="";
			for(int i = 0 ; i < targetList.length ; i++){
				//write the current target schema to file
				PrintWriter targetWriter = new PrintWriter("inputs/target.txt","UTF-8");
				currTarget = targetList[i].trim();
				targetWriter.write(currTarget);
				targetWriter.close();
				
				if(srcSchema.equals("") || currTarget.equals("")){
					//if we have either an empty source or target
					//then move on because that will not return any
					//results
					continue;
				}else{
					//then call SPSM & store results
					makeCallToSPSM();
					recordSerialisedResults(results,currTarget);
				}
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}		
		
	}
	
	//makes call to SPSM through using .sh file
	public void makeCallToSPSM(){
		System.out.println("Calling SPSM");
		
		//call SPSM by executing the appropriate bash file
		try {
			final ProcessBuilder pb = new ProcessBuilder("/bin/sh","call-spsm.sh");
			pb.directory(new File("spsm/s-match/bin"));
			
			//start the process of executing file and wait for 
			//it to finish before terminating the program
			final Process p = pb.start();
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//then record the results from the .ser file returned from spsm
	@SuppressWarnings("unchecked")
	public void recordSerialisedResults(ArrayList<Match_Struc> results,String targetSchema){
		System.out.println("Recording Results");
		
		//get the serialised content from the .ser file
		//and store it in a IContextMapping object var
		IContextMapping<INode> mapping = null;
		
		//read in the object
		try{
			FileInputStream fIn = new FileInputStream("outputs/serialised-results.ser");
			ObjectInputStream inStream = new ObjectInputStream(fIn);
			
			mapping = (IContextMapping<INode>) inStream.readObject();
			
			inStream.close();
			fIn.close();
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
		///////////////////////////////////////////////////////////////////////////////////
		
		
		//start picking data out of the object for this target
		Match_Struc newMatch;
	
		double similarity = mapping.getSimilarity();
		newMatch = new Match_Struc(similarity,targetSchema);
		
		String[] currMatch;
		
		//loop through each of the individual matching elements w relations
		for (IMappingElement<INode> mappingElement : mapping) {
			String sourceConceptName = getNodePathString(mappingElement.getSource());
			String targetConceptName = getNodePathString(mappingElement.getTarget());
			String relation = Character.toString(mappingElement.getRelation());
			
			currMatch = new String[]{sourceConceptName,relation,targetConceptName};
			
			newMatch.addMatch(currMatch);
		}
		
		//then add this new match to our overall list of results, if there have been matches only
		if(newMatch.getNumMatches() != 0){
			results.add(newMatch);
		}
		
	}
	
	//used by recordSerialisedResults to return string
	//of the node
    public String getNodePathString(INode node) {
        StringBuilder sb = new StringBuilder();

        sb.insert(0, node.nodeData().getName());
        node = node.getParent();

        while (node != null) {
            sb.insert(0, node.nodeData().getName() + ",");
            node = node.getParent();
        }

        return sb.toString();
    }
}