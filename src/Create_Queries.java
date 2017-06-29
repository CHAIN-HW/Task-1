import java.util.*;

public class Create_Queries {

	public static void main(String[] args){

		Call_SPSM spsmCall;
		Best_Match_Results filterResCall;
		Create_Queries queryCreator;
		ArrayList<Match_Struc> finalRes;
		String target, source;
		
		spsmCall = new Call_SPSM();
		filterResCall = new Best_Match_Results();
		queryCreator = new Create_Queries();
		
//		source = "conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
//		target = "conference(paper(title,document(date(day,month,year),writer(name(first,second)))))";
		
		source = "auto(brand,name,color)";
		target = "car(year,brand,colour)";
	
//		source="author";
//		target="writer";
		
		finalRes = new ArrayList<Match_Struc>();
		
		finalRes = spsmCall.getSchemas(finalRes, source, target);
		
		Match_Struc test = finalRes.get(0);
		System.out.println(test.getSimValue());
		
		ArrayList<String[]> testParts = test.getMatches();
		for(int i = 0 ; i < testParts.size() ; i++){
			String[] curr = testParts.get(i);
			
			System.out.println(curr[0]);
			System.out.println(curr[1]);
			System.out.println(curr[2]);
		}
		
//		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.6, 0);
//		finalRes = queryCreator.prepare(finalRes);	
//		
//		//then check what we have stored as our repaired schemas
//		//testing purposes only!!
//		System.out.println("\nFinal Repaired Schema(s): ");
//		for(int i = 0 ; i < finalRes.size() ; i++){
//			System.out.println(finalRes.get(i).getRepairedSchema());
//		}
		
	}
	
	public ArrayList<Match_Struc> prepare(ArrayList<Match_Struc> matchRes){
		System.out.println("Repairing Target Schemas");
		
		//for each of the targets, create a tree and create repaired schema
		//and store that into the match structure
		for(int i = 0 ; i < matchRes.size() ; i++){
			
			//current match
			Match_Struc currMatch = matchRes.get(i);
			currMatch = repairSchema(currMatch);
		}
		
		return matchRes;
	}
	
	public Match_Struc repairSchema(Match_Struc matchStructure){
		
		ArrayList<String[]> matchDetails = matchStructure.getMatches();
		
		//start off by going through the list of match details
		//creating/adding to a tree for that target schema
		Node targetRoot=null;
		
		for(int i = 0 ; i < matchDetails.size() ; i++){
				
			String[] currMatchDetails = matchDetails.get(i);
			String targetConcept = currMatchDetails[2];
			
			//System.out.println("Modifying tree for " + targetConcept);
			targetRoot = modifyRepairedTree(targetRoot, targetConcept);
		}
		
		//after we have been through all details, we add to structure
		matchStructure.setRepairedSchemaTree(targetRoot);
		matchStructure.setRepairedSchema(targetRoot.printTree());
		
		//return the updated match structure
		return matchStructure;
	}
	
	public Node modifyRepairedTree(Node root, String concept){
		//System.out.println("Current match detail is "+concept);
		//split the concept string
		//so that we have the different parents/children
		String[] conceptParts = concept.split(",");
		
		if(root == null){
			//if the root is null, then our tree is empty
			//add the root as the first element in the conceptParts
			root = new Node(conceptParts[0]);
		}
		
		//keep a note of who the parent is
		//since we will be working our way down the list
		Node parent = root;
		 
		//make sure we have more than one element in the list
		if(conceptParts.length > 1){
			
			//then we can skip the first element in the array
			//as it will always be the name of the predicate
			//i.e. our root!	
			for(int i = 1 ; i < conceptParts.length ; i++){
				
				//System.out.println("Parent is "+parent.getValue());
				
				Node currNode = new Node(conceptParts[i]);
				
				//for each of these, we want to check if this child exists
				//by looking at its parents list of children
				
				ArrayList<String> childrenVals = parent.getChildrenValues();
				
				if(!(childrenVals.contains(currNode.getValue()))){
					//doesn't yet exist, add to list of children
					//and set node to be new parent
					//System.out.println("adding new child " + currNode.getValue());
					parent.addChild(currNode);
					parent = currNode;
				}else{
					//this is already in our structure as a child
					//so we set it to be the parent
					int newParentIndex = childrenVals.indexOf(currNode.getValue());
					Node tmpCurr = parent.getChildren().get(newParentIndex);
					
					parent = tmpCurr;
				}
			
				//System.out.println(root.printTree());
			}
			
		}
		
		//return the modified tree
		return root;
	}	
	
	public void createQuery(){
		
	}
}
