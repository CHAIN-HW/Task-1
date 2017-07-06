import java.io.*;

import java.util.*;
import org.junit.*;

/*
 * Responsible for testing the output results from
 * Call_SPSM.java which takes in source and target
 * schemas and then calls SPSM before reading in
 * results as serialised object
 */
public class SPSM_Test_Cases {
	private Call_SPSM methodCaller;
	private ArrayList<Match_Struc> results;
	private String source,target;
	
	//for writing results
	private static File testRes;
	private PrintWriter fOut;
	private static boolean alreadyWritten;
	
	@BeforeClass
	public static void beforeAll(){
		alreadyWritten = false;
		try{
			testRes = new File("outputs/testing/Call_SPSM_Tests.txt");
			testRes.createNewFile();
			
			new PrintWriter("outputs/testing/Call_SPSM_Tests.txt").close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	@Before
	public void setup(){
		methodCaller = new Call_SPSM();
		results = new ArrayList<Match_Struc>();
		
		try{
			fOut = new PrintWriter(new FileWriter(testRes,true));
			
			if(alreadyWritten==false){
				fOut.write("Testing Results for Call_SPSM.java\n\n");
				alreadyWritten = true;
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Test
	//no source or target
	public void test11() {
		source=""; 
		target="";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 1.1\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		fOut.write("Target: "+target+"\n");
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			
			
			fOut.write("Expected Result: results.size() == 0 \n");
			fOut.write("Actual Result: results.size() == "+results.size()+"\n\n");
			
			//assertEquals(0,results.size());
		}
	}
	
	@Test
	//no target but one source
	public void test21(){
		source="author(name)";
		target="";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 2.1\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			fOut.write("Expected Result: results.size() == 0 \n");
			fOut.write("Actual Result: results.size() == "+results.size()+"\n\n"); 
			//assertEquals(0,results.size());
		}
	}
	
	@Test
	//no source but one target
	public void test22(){
		source="";
		target="author(name)";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 2.2\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			
			fOut.write("Expected Result: results.size() == 0 \n");
			fOut.write("Actual Result: results.size() == "+results.size()+"\n\n"); 
			
			//assertEquals(0,results.size());
		}
	}
	
	@Test
	//one source and one target
	public void test23(){
		source="author(name)";
		target="author(name)";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 2.3\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		fOut.write("Expected Result: similarity == 1.0"+" & numMatches == 2 \n");
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() > 0){
				Match_Struc res = results.get(0);
				
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
//				
//				assertEquals(2,res.getNumMatches());
//				assertTrue(1.0 == res.getSimValue());
//				assertEquals("author(name)",res.getDatasetSchema());
			}else{
				fOut.write("Empty Results! \n\n");
			}
			 
		}
		
	}
	
	@Test
	//one source w multiple targets
	public void test24(){
		source="author(name)";
		target="document(title,author) ; author(name,document) ; reviewAuthor(firstname,lastname,review)";
		
		methodCaller.getSchemas(results, source, target);
		
		int[] answer = {2,2};
		double[] simValues = {1.0,0.75};
		
		fOut.write("Test 2.4\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() > 0){
				for(int i = 0 ; i < results.size() ; i++){
					
					Match_Struc currRes = results.get(i);

					
					fOut.write("Target: "+currRes.getDatasetSchema()+"\n");
					fOut.write("Expected Result: similarity == "+simValues[i]+" & numMatches == "+answer[i]+"\n");
					fOut.write("Actual Result: similarity == "+currRes.getSimValue()+" & numMatches == "+currRes.getNumMatches()+"\n\n"); 
//
//					assertEquals(answer[i],currRes.getNumMatches());
//					assertTrue(currRes.getSimValue() == simValues[i]);
				}
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
		
	}
	
	@Test
	//one source w two targets
	public void test31(){
		source="author(name)";
		target="document(title,author) ; conferenceMember(name)";
		
		methodCaller.getSchemas(results,source,target);
		
		
		fOut.write("Test 3.1\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		 
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			fOut.write("Expected Result: results.size() == 0 \n");
			fOut.write("Actual Result: results.size() == "+results.size()+"\n\n");
			
//			assertEquals(0,results.size());
		}
	}
	
	@Test
	public void test32(){
		source="author(name)";
		target="author(name) ; document(title,author)";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 3.2\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() > 0){
				Match_Struc currRes = results.get(0);
				
				fOut.write("Target: "+currRes.getDatasetSchema()+"\n");
				fOut.write("Expected Result: similarity == 1.0"+" & numMatches == 2 \n");
				fOut.write("Actual Result: similarity == "+currRes.getSimValue()+" & numMatches == "+currRes.getNumMatches()+"\n\n"); 
//			
//				assertEquals(2,currRes.getNumMatches());
//				assertTrue(currRes.getSimValue() == 1.0);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
			
		}
	}
	
	@Test
	public void test33(){
		source="author(name)";
		target="author(name) ; document(title,author) ; paperWriter(firstname,surname,paper) ; reviewAuthor(firstname,lastname,review)";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 3.3\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		int[] answer={2,2,2};
		double[] simVals = {1.0,0.5,0.75};
		
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				for(int i = 0 ; i < results.size() ; i++){
					Match_Struc currRes = results.get(i);
					
					fOut.write("Target: "+currRes.getDatasetSchema()+"\n");
					fOut.write("Expected Result: similarity == "+simVals[i]+" & numMatches == "+answer[i]+"\n");
					fOut.write("Actual Result: similarity == "+currRes.getSimValue()+" & numMatches == "+currRes.getNumMatches()+"\n\n");
					
//					assertEquals(answer[i],currRes.getNumMatches());
//					assertTrue(currRes.getSimValue() == simVals[i]);
					
				}
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test41(){
		source="author";
		target="writer";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 4.1\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
	
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				
				fOut.write("Target: "+res.getDatasetSchema()+"\n");
				fOut.write("Expected Result: similarity == 1.0"+" & numMatches == 1 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
				
//				assertEquals(1,res.getNumMatches());
//				assertTrue(res.getSimValue() == 1.0);
//				assertEquals(target, res.getDatasetSchema());
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test42(){
		source="author";
		target="document";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 4.2\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			
			fOut.write("Expected Result: results.size() == 0 \n");
			fOut.write("Actual Result: results.size() == "+results.size()+"\n\n");
			
//			assertEquals(0,results.size());
			
		}
	}
	
	@Test
	public void test43(){
		source="author(name)";
		target="document(name)";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 4.3\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			fOut.write("Expected Result: results.size() == 0 \n");
			fOut.write("Actual Result: results.size() == "+results.size()+"\n\n"); 
		
//			assertEquals(0,results.size());
			
		}
	}
	
	@Test
	public void test44(){
		source="author(name)";
		target="reviewWriter(review,name)";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 4.4\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() > 0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.5"+" & numMatches == 2 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(2,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.5);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test45(){
		source="reviewWriter(document,date,name)";
		target="author(name,email,coAuthors,writePaper,submitPapers,review)";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 4.5\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results == null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() > 0){
				Match_Struc res = results.get(0);
				
				fOut.write("Target: "+res.getDatasetSchema()+"\n");
				fOut.write("Expected Result: similarity == 0.5"+" & numMatches == 3 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(3,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.5);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test51(){
		source="review(date(day,month,year))";
		target="document(date(day,month,year))";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 5.1\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() > 0){
				Match_Struc res = results.get(0);
				
				fOut.write("Target: "+res.getDatasetSchema()+"\n");
				fOut.write("Expected Result: similarity == 1.0"+" & numMatches == 5 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
				
//				assertEquals(5,res.getNumMatches());
//				assertTrue(res.getSimValue() == 1.0);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test52(){
		source = "review(publication(day,month,year))";
		target= "review(date(day,month,year))";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 5.2\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() > 0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.6"+" & numMatches == 5 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(5,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.6);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test53(){
		source="review(publication(day,month,year))";
		target= "document(date(day,month,year))";
		
		methodCaller.getSchemas(results, source, target);
		
		
		fOut.write("Test 5.3\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			fOut.write("Expected Result: results.size() == 0 \n");
			fOut.write("Actual Result: results.size() == "+results.size()+"\n\n"); 
		
//			assertEquals(0,results.size());
		}
	}
	
	@Test
	public void test54(){
		source="review(category(day,month,year))";
		target="review(date(day,month,year))";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 5.4\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		fOut.write("Expected Result: similarity == 0.19999999999999996"+" & numMatches == 1 \n");
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() > 0){
				Match_Struc res = results.get(0);
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
				
//				assertEquals(1,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.19999999999999996);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test55(){
		source="review(category(day,month,year))";
		target="document(date(day,month,year))";
		
		methodCaller.getSchemas(results,source,target);	
		
		
		fOut.write("Test 5.5\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				fOut.write("Expected Result: similarity == 0.9999999999999998"+" & numMatches == 1 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(1,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.09999999999999998);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
			
		}
	}
	
	@Test
	public void test56(){
		source="conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
		target="conference(paper(title,document(date(day,month,year),writer(name(first,second)))))";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 5.6\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				fOut.write("Expected Result: similarity == 0.625"+" & numMatches == 12 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
//				assertEquals(12,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.625);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test57(){
		source="conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
		target="conference(paper(title,document(category(day,month,year),writer(name(first,second)))))";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 5.7\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
	
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.45833333333333337"+" & numMatches == 8 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(8,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.45833333333333337);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test58(){
		source="conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
		target="event(paper(title,document(category(day,month,year),writer(name(first,second)))))";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 5.8\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
	
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			
			fOut.write("Expected Result: results.size() == 0 \n");
			fOut.write("Actual Result: results.size() == "+results.size()+"\n\n"); 
			
//			assertEquals(0,results.size());
			
		}
	}
	
	@Test
	public void test61(){
		source="conferenceDocument(nameOfAuthor)";
		target="conferenceReview(authorName)";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 6.1\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
				
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.5"+" & numMatches ==2 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(2,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.5);

			}else{
				fOut.write("Empty Results \n\n");
			}
			
		}
	}
	
	@Test 
	public void test62(){
		source="conference_document(name_of_author)";
		target="conference_review(author_name)";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 6.2\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
				
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.5"+" & numMatches == 2 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
				
//				assertEquals(2,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.5);
				
			}else{
				fOut.write("Empty Results \n\n");
			}
			
		}
	}
	
	@Test
	public void test63(){
		source="conference_document(name_of_author)";
		target="ConferenceReview(authorName)";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 6.3\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
			
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.5"+" & numMatches == 2 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(2,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.5);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test64(){
		source="conference document(name of author)";
		target="conference review(author name)";
		
		methodCaller.getSchemas(results,source,target);
		
		fOut.write("Test 6.4\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
				
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.5"+" & numMatches == 2 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
				
//				assertEquals(2,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.5);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test65(){
		source="conference document(nameOfAuthor)";
		target="conference review(authorName)";
		
		methodCaller.getSchemas(results,source,target);
	
		fOut.write("Test 6.5\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size()>0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.5"+" & numMatches == 2 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(2,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.5);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test66(){
		source="conferencedocument(nameofauthor)";
		target="conference review(authorname)";
		
		methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 6.6\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results==null){
			fOut.write("Null Results! \n\n");
		}else{
			if(results.size() >0){
				Match_Struc res = results.get(0);
				
				fOut.write("Expected Result: similarity == 0.5"+" & numMatches == 2 \n");
				fOut.write("Actual Result: similarity == "+res.getSimValue()+" & numMatches == "+res.getNumMatches()+"\n\n");
			
//				assertEquals(2,res.getNumMatches());
//				assertTrue(res.getSimValue() == 0.5);
				
			}else{
				fOut.write("Empty Results! \n\n");
			}
			
		}
	}
	
	@Test
	public void test67(){
		source="auto(brand,name,color)";
		target="car(year,brand,colour)";
		
		results = methodCaller.getSchemas(results, source, target);
		
		fOut.write("Test 6.7\n");
		fOut.write("Calling SPSM with source, "+source + " & target(s), " +target+"\n");
		
		if(results==null){
			fOut.write("Empty Results! \n\n");
		}else{
			
			fOut.write("Expected Result: results == null \n");
			fOut.write("Actual Result: results == "+results+"\n\n"); 
		
//			assertEquals(results, null);
			
		}
	}
	
	
	@After
	public void cleanUp(){
		fOut.close();
	}

}







