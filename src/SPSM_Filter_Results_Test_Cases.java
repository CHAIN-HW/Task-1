import static org.junit.Assert.*;


import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.*;

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
	private static int counter;
	
	//for writing results
	private static File testRes;
	private PrintWriter fOut;
	private static boolean alreadyWritten;
	
	@BeforeClass
	public static void beforeAll(){
		alreadyWritten = false;
		counter=1;
		try{
			testRes = new File("outputs/testing/Task_1_Tests.txt");
			testRes.createNewFile();
			
			new PrintWriter("outputs/testing/Task_1_Tests.txt").close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
		
	@Before
	public void setup(){
		spsmCall = new Call_SPSM();
		filterResCall = new Best_Match_Results();
		
		try{
			fOut = new PrintWriter(new FileWriter(testRes,true));
			
			if(alreadyWritten==false){
				fOut.write("Testing Results for Call_SPSM.java & Best_Match_Results.java\n\n");
				alreadyWritten = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void successMultiCall(){
		source = "author(name)";
		target = "author(name) ; document(title,author) ; paperWriter(firstname,surname,paper) ; reviewAuthor(firstname,lastname,review)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		
		assertEquals(3,finalRes.size());

		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.6, 0);
//		assertEquals(2,finalRes.size());
		
//		double[] expectedRes = new double[]{1.0,0.75};
//		for(int i = 0 ; i < finalRes.size() ; i++){
//			Match_Struc currMatch = finalRes.get(i);
//			Assert.assertTrue(expectedRes[i] == currMatch.getSimValue());
//		}
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling SPSM with source, "+source+" & target, "+target+"\n");
		fOut.write("Calling with threshold: "+0.6+" & limit: "+0+" \n");
		
		fOut.write("Expected Result: results.size() == 2 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;

		
		
	}
	
	@Test
	public void successSingleCall(){
		source="author(name)";
		target="author(name)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		
		assertEquals(1,finalRes.size());

		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.6, 0);
//		assertEquals(1,finalRes.size());
//		
//		double[] expectedRes = new double[]{1.0};
//		for(int i = 0 ; i < finalRes.size() ; i++){
//			Match_Struc currMatch = finalRes.get(i);
//			Assert.assertTrue(expectedRes[i] == currMatch.getSimValue());
//		}
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling SPSM with source, "+source+" & target, "+target+"\n");
		fOut.write("Calling with threshold: "+0.6+" & limit: "+0+" \n");
		
		fOut.write("Expected Result: results.size() == 1 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
	}
	
	@Test
	public void failSingleCall(){
		source="author(name)";
		target="document(title,author)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
//		assertEquals(0,finalRes.size());
		
		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.0, 0);
//		assertEquals(0,finalRes.size());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling SPSM with source, "+source+" & target, "+target+"\n");
		fOut.write("Calling with threshold: "+0.0+" & limit: "+0+" \n");
		
		fOut.write("Expected Result: results.size() == 0 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
	}
	
	@Test
	public void successWithLimit(){
		source = "author(name)";
		target = "author(name) ; document(title,author) ; paperWriter(firstname,surname,paper) ; reviewAuthor(firstname,lastname,review)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
		
//		assertEquals(3,finalRes.size());

		finalRes = filterResCall.getThresholdAndFilter(finalRes, 0.0, 2);
//		assertEquals(2,finalRes.size());
//		
//		double[] expectedRes = new double[]{1.0,0.75};
//		for(int i = 0 ; i < finalRes.size() ; i++){
//			Match_Struc currMatch = finalRes.get(i);
//			Assert.assertTrue(expectedRes[i] == currMatch.getSimValue());
//		}
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling SPSM with source, "+source+" & target, "+target+"\n");
		fOut.write("Calling with threshold: "+0.0+" & limit: "+2+" \n");
		
		fOut.write("Expected Result: results.size() == 2 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
	}
	
	@Test
	public void failWithLimit(){
		source="author(name)";
		target="document(title,author)";
		finalRes = new ArrayList<Match_Struc>();
		
		spsmCall.getSchemas(finalRes, source, target);
//		assertEquals(0,finalRes.size());
		
		finalRes = filterResCall.getThresholdAndFilter(finalRes,0.0,2);
//		assertEquals(0,finalRes.size());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling SPSM with source, "+source+" & target, "+target+"\n");
		fOut.write("Calling with threshold: "+0.0+" & limit: "+2+" \n");
		
		fOut.write("Expected Result: results.size() == 0 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
	}
	
	@After
	public void cleanUp(){
		fOut.close();
	}

}
