import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/*
 * Responsible for testing the whole of task 1 which involves
 * methods from both Call_SPSM.java and Best_Match_Results.java
 * to make sure that we get results from SPSM that have been filtered
 * and sorted
 */
public class SPSM_Filter_Results_Test_Cases {

	private Call_SPSM spsmCall;
	private Best_Match_Results filterResCall;
	private ArrayList<Match_Struc> finalRes;
	private String target, source;
	
	@Before
	public void setup(){
		spsmCall = new Call_SPSM();
		filterResCall = new Best_Match_Results();
	}
	
	@Test
	public void successMultiCall(){
		source = "author(name)";
		target = "author(name) ; document(title,author) ; paperWriter(firstname,surname,paper) ; reviewAuthor(firstname,lastname,review)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		
		assertEquals(3,finalRes.size());

		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.6, 0);
		assertEquals(2,finalRes.size());
		
		double[] expectedRes = new double[]{1.0,0.75};
		for(int i = 0 ; i < finalRes.size() ; i++){
			Match_Struc currMatch = finalRes.get(i);
			Assert.assertTrue(expectedRes[i] == currMatch.getSimValue());
		}
	}
	
	@Test
	public void successSingleCall(){
		source="author(name)";
		target="author(name)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		
		assertEquals(1,finalRes.size());

		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.6, 0);
		assertEquals(1,finalRes.size());
		
		double[] expectedRes = new double[]{1.0};
		for(int i = 0 ; i < finalRes.size() ; i++){
			Match_Struc currMatch = finalRes.get(i);
			Assert.assertTrue(expectedRes[i] == currMatch.getSimValue());
		}
	}
	
	@Test
	public void failSingleCall(){
		source="author(name)";
		target="document(title,author)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		assertEquals(0,finalRes.size());
		
		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.0, 0);
		assertEquals(0,finalRes.size());
	}
	
	@Test
	public void successWithLimit(){
		source = "author(name)";
		target = "author(name) ; document(title,author) ; paperWriter(firstname,surname,paper) ; reviewAuthor(firstname,lastname,review)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		
		assertEquals(3,finalRes.size());

		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.0, 2);
		assertEquals(2,finalRes.size());
		
		double[] expectedRes = new double[]{1.0,0.75};
		for(int i = 0 ; i < finalRes.size() ; i++){
			Match_Struc currMatch = finalRes.get(i);
			Assert.assertTrue(expectedRes[i] == currMatch.getSimValue());
		}
	}
	
	@Test
	public void failWithLimit(){
		source="author(name)";
		target="document(title,author)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		assertEquals(0,finalRes.size());
		
		finalRes = filterResCall.getThresholdAndFilter(finalRes,0.0,2);
		assertEquals(0,finalRes.size());
	}

}
