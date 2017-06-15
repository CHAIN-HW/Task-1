import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/*
 * Responsible for testing the Best_Match_Results.java file
 * to make sure that when we pass in the results we get results 
 * that are over the threshold value that are sorted and we only
 * have n number of results as requested
 */
public class Match_Results_Test_Cases {

	private Best_Match_Results methodCall;
	private ArrayList<Match_Struc> finalRes;
	
	@Before
	public void setup(){
		methodCall = new Best_Match_Results();
		finalRes = new ArrayList<Match_Struc>();
	}
	
	@Test
	public void emptyMatches(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		finalRes = methodCall.getThresholdAndFilter(res, 0.2, 0);
		
		assertEquals(0,finalRes.size());
	}
	
	@Test
	public void singleSuccMatch(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 0.1, 0);
		
		assertEquals(1,finalRes.size());
	}
	
	@Test
	public void singleFailMatch(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 0.5, 0 );
		
		assertEquals(0,finalRes.size());
	}
	
	@Test
	public void multiSuccMatch(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		res.add(new Match_Struc(0.7,"author(name,document)"));
		res.add(new Match_Struc(0.1,"author(name,document)"));
		res.add(new Match_Struc(0.9,"author(name,document)"));
		res.add(new Match_Struc(0.5,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 0.6, 0);
		
		assertEquals(2,finalRes.size());
		assertTrue(finalRes.get(0).getSimValue() >= finalRes.get(1).getSimValue());
	}
	
	@Test
	public void multiSuccMatch2(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		res.add(new Match_Struc(0.7,"author(name,document)"));
		res.add(new Match_Struc(0.1,"author(name,document)"));
		res.add(new Match_Struc(0.9,"author(name,document)"));
		res.add(new Match_Struc(0.5,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 0.2, 0);
		
		assertEquals(4,finalRes.size());
		
		for(int i = 0 ; i < finalRes.size()-1 ; i++){
			assertTrue(finalRes.get(i).getSimValue() >= finalRes.get(i+1).getSimValue());
		}
	}
	
	@Test
	public void multiFailMatch(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		res.add(new Match_Struc(0.7,"author(name,document)"));
		res.add(new Match_Struc(0.1,"author(name,document)"));
		res.add(new Match_Struc(0.9,"author(name,document)"));
		res.add(new Match_Struc(0.5,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 1.0, 0);
		
		assertEquals(0,finalRes.size());
	}
	
	@Test
	public void successWithLimit(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		res.add(new Match_Struc(0.7,"author(name,document)"));
		res.add(new Match_Struc(0.1,"author(name,document)"));
		res.add(new Match_Struc(0.9,"author(name,document)"));
		res.add(new Match_Struc(0.5,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 0.2, 3);
		
		assertEquals(3,finalRes.size());
	}
	
	@Test
	public void successWithLargeLimit(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		res.add(new Match_Struc(0.7,"author(name,document)"));
		res.add(new Match_Struc(0.1,"author(name,document)"));
		res.add(new Match_Struc(0.9,"author(name,document)"));
		res.add(new Match_Struc(0.5,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 0.2, 5);
		
		assertEquals(4,finalRes.size());
	}
	
	@Test
	public void failWithLimit(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		res.add(new Match_Struc(0.7,"author(name,document)"));
		res.add(new Match_Struc(0.1,"author(name,document)"));
		res.add(new Match_Struc(0.9,"author(name,document)"));
		res.add(new Match_Struc(0.5,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 1.0, 5);
		
		assertEquals(0,finalRes.size());
	}
	

}
