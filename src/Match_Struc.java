import java.util.ArrayList;

/*
 * Structure that is being used to store
 * the results from SPSM.
 * 
 * This structure will have a similarity value,
 * the name of the target, a list of the individual
 * matches and relations as well as storing how many
 * individual matches this target has
 */
public class Match_Struc{
	
	//fields
	private double similarity;
	private String dataset;
	private ArrayList<String[]> matches;
	private int numMatches;
	
	//constructor
	public Match_Struc(double sim, String targetSchema){
		similarity=sim;
		dataset=targetSchema;
		matches=new ArrayList<String[]>();
		numMatches=0;
	}
	
	//public methods
	public void setSimilarity(double sim){
		similarity=sim;
	}
	
	public void setDatasetSchema(String targetSchema){
		dataset=targetSchema;
	}
	
	public void addMatch(String[] match){
		matches.add(match); 
		numMatches++;
	}
	
	public int getNumMatches(){
		return numMatches;
	}
	
	public double getSimValue(){
		return similarity;
	}
	
	public String getDatasetSchema(){
		return dataset;
	}
	
	public ArrayList<String[]> getMatches(){
		return matches;
	}
	
	public String[] getMatchAtIndex(int index){
		return matches.get(index);
	}
}