import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
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
	private static int counter;
	
	//for writing results
	private static File testRes;
	private PrintWriter fOut;
	private static boolean alreadyWritten;
	
	@BeforeClass
	public static void beforeAll(){
		alreadyWritten=false;
		counter=1;
		
		try{
			testRes = new File("outputs/testing/Filter_Limit_Test.txt");
			testRes.createNewFile();
			
			new PrintWriter("outputs/testing/Filter_Limit_Test.txt").close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Before
	public void setup(){
		methodCall = new Best_Match_Results();
		finalRes = new ArrayList<Match_Struc>();
		
		try{
			fOut = new PrintWriter(new FileWriter(testRes,true));
			
			if(alreadyWritten==false){
				fOut.write("Testing Results for Best_Match_Results.java\n\n");
				alreadyWritten = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Test
	public void emptyMatches(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		finalRes = methodCall.getThresholdAndFilter(res, 0.2, 0);
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+0.2+" & limit: "+0+" on an empty list of matches \n");
		
		fOut.write("Expected Result: results.size() == 0 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
		
		//assertEquals(0,finalRes.size());
		
	}
	
	@Test
	public void singleSuccMatch(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 0.1, 0);
		
		//assertEquals(1,finalRes.size());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+0.1+" & limit: "+0+" on a single match \n");
		
		fOut.write("Expected Result: results.size() == 1 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
	}
	
	@Test
	public void singleFailMatch(){
		ArrayList<Match_Struc> res = new ArrayList<Match_Struc>();
		res.add(new Match_Struc(0.2,"author(name,document)"));
		
		finalRes = methodCall.getThresholdAndFilter(res, 0.5, 0 );
		
		//assertEquals(0,finalRes.size());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+0.5+" & limit: "+0+" on a single match \n");
		
		fOut.write("Expected Result: results.size() == 0 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
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
		
//		assertEquals(2,finalRes.size());
//		assertTrue(finalRes.get(0).getSimValue() >= finalRes.get(1).getSimValue());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+0.6+" & limit: "+0+" on multiple matches \n");
		
		fOut.write("Expected Result: results.size() == 2 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
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
		
//		assertEquals(4,finalRes.size());
//		
//		for(int i = 0 ; i < finalRes.size()-1 ; i++){
//			assertTrue(finalRes.get(i).getSimValue() >= finalRes.get(i+1).getSimValue());
//		}
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+0.2+" & limit: "+0+" on multiple matches \n");
		
		fOut.write("Expected Result: results.size() == 4 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
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
		
//		assertEquals(0,finalRes.size());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+1.0+" & limit: "+0+" on multiple matches \n");
		
		fOut.write("Expected Result: results.size() == 0 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
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
		
//		assertEquals(3,finalRes.size());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+0.2+" & limit: "+3+" on multiple matches \n");
		
		fOut.write("Expected Result: results.size() == 3 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
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
		
//		assertEquals(4,finalRes.size());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+0.2+" & limit: "+5+" on multiple matches \n");
		
		fOut.write("Expected Result: results.size() == 4 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
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
		
//		assertEquals(0,finalRes.size());
		
		fOut.write("Test "+counter+"\n");
		fOut.write("Calling with threshold: "+1.0+" & limit: "+5+" on multiple matches \n");
		
		fOut.write("Expected Result: results.size() == 0 \n");
		fOut.write("Actual Result: results.size() == "+finalRes.size()+"\n\n");
		
		counter++;
	}
	
	@After
	public void cleanUp(){
		fOut.close();
	}
	

}
