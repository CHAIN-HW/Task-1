import static org.junit.Assert.*;

import java.util.*;
import org.junit.*;


/*
 * Responsible for testing the output results from
 * Call_SPSM.java which takes in source and target
 * schemas and then calls SPSM
 */
public class SPSM_Test_Cases {
	
	private Call_SPSM methodCaller;
	private ArrayList<Match_Struc> results;
	String source,target;
	
	@Before
	public void setup(){
		methodCaller = new Call_SPSM();
		results = new ArrayList<Match_Struc>();
	}

	@Test
	//no source or target
	public void test1() {
		source=""; 
		target="";
		
		methodCaller.getSchemas(results, source, target);
		
		assertEquals(0,results.size());
	}
	
	@Test
	//no target but one source
	public void test21(){
		source="author(name)";
		target="";
		
		methodCaller.getSchemas(results,source,target);
		
		assertEquals(0,results.size());
	}
	
	@Test
	//no source but one target
	public void test22(){
		source="";
		target="author(name)";
		
		methodCaller.getSchemas(results,source,target);
		
		assertEquals(0,results.size());
	}
	
	@Test
	//one source and one target
	public void test23(){
		source="author(name)";
		target="author(name)";
		
		methodCaller.getSchemas(results,source,target);
		Match_Struc res = results.get(0);
		
		assertEquals(2,res.getNumMatches());
		assertTrue(1.0 == res.getSimValue());
	}
	
	@Test
	//one source w multiple targets
	public void test24(){
		source="author(name)";
		target="document(title,author) ; author(name,document) ; reviewAuthor(firstname,lastname,review)";
		
		methodCaller.getSchemas(results, source, target);
		
		int[] answer = {2,2};
		double[] simValues = {1.0,0.75};
		
		for(int i = 0 ; i < results.size() ; i++){
			Match_Struc currRes = results.get(i);
			assertEquals(answer[i],currRes.getNumMatches());
			assertTrue(currRes.getSimValue() == simValues[i]);
		}
	}
	
	@Test
	//one source w two targets
	public void test31(){
		source="author(name)";
		target="document(title,author) ; conferenceMember(name)";
		
		methodCaller.getSchemas(results,source,target);
		assertEquals(0,results.size());
	}
	
	@Test
	public void test32(){
		source="author(name)";
		target="author(name) ; document(title,author)";
		
		methodCaller.getSchemas(results, source, target);
		
		Match_Struc currRes = results.get(0);
		assertEquals(2,currRes.getNumMatches());
		assertTrue(currRes.getSimValue() == 1.0);
	}
	
	@Test
	public void test33(){
		source="author(name)";
		target="author(name) ; document(title,author) ; paperWriter(firstname,surname,paper) ; reviewAuthor(firstname,lastname,review)";
		
		methodCaller.getSchemas(results,source,target);
		
		int[] answer={2,2,2};
		double[] simVals = {1.0,0.5,0.75};
		for(int i = 0 ; i < results.size() ; i++){
			Match_Struc currRes = results.get(i);
			assertEquals(answer[i],currRes.getNumMatches());
			assertTrue(currRes.getSimValue() == simVals[i]);
		}
	}
	
	@Test
	public void test41(){
		source="author";
		target="writer";
		
		methodCaller.getSchemas(results,source,target);
		
		Match_Struc res = results.get(0);
		assertEquals(1,res.getNumMatches());
		assertTrue(res.getSimValue() == 1.0);
	}
	
	@Test
	public void test42(){
		source="author";
		target="document";
		
		methodCaller.getSchemas(results, source, target);
		assertEquals(0,results.size());
	}
	
	@Test
	public void test43(){
		source="author(name)";
		target="document(name)";
		
		methodCaller.getSchemas(results, source, target);
		assertEquals(0,results.size());
	}
	
	@Test
	public void test44(){
		source="author(name)";
		target="reviewWriter(review,name)";
		
		methodCaller.getSchemas(results, source, target);
		
		Match_Struc res = results.get(0);
		assertEquals(2,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.5);
	}
	
	@Test
	public void test45(){
		source="reviewWriter(document,date,name)";
		target="author(name,email,coAuthors,writePaper,submitPapers,review)";
		
		methodCaller.getSchemas(results,source,target);
		
		Match_Struc res = results.get(0);
		assertEquals(3,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.5);
	}
	
	@Test
	public void test51(){
		source="review(date(day,month,year))";
		target="document(date(day,month,year))";
		
		methodCaller.getSchemas(results,source,target);
		
		Match_Struc res = results.get(0);
		assertEquals(5,res.getNumMatches());
		assertTrue(res.getSimValue() == 1.0);
	}
	
	@Test
	public void test52(){
		source = "review(publication(day,month,year))";
		target= "review(date(day,month,year))";
		
		methodCaller.getSchemas(results, source, target);
		
		Match_Struc res = results.get(0);
		assertEquals(5,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.6);
	}
	
	@Test
	public void test53(){
		source="review(publication(day,month,year))";
		target= "document(date(day,month,year))";
		
		methodCaller.getSchemas(results, source, target);
		assertEquals(0,results.size());
	}
	
	@Test
	public void test54(){
		source="review(category(day,month,year))";
		target="review(date(day,month,year))";
		
		methodCaller.getSchemas(results, source, target);
		
		Match_Struc res = results.get(0);
		assertEquals(1,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.19999999999999996);
	}
	
	@Test
	public void test55(){
		source="review(category(day,month,year))";
		target="document(date(day,month,year))";
		
		methodCaller.getSchemas(results,source,target);	
		
		Match_Struc res = results.get(0);
		assertEquals(1,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.09999999999999998);
	}
	
	@Test
	public void test56(){
		source="conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
		target="conference(paper(title,document(date(day,month,year),writer(name(first,second)))))";
		
		methodCaller.getSchemas(results, source, target);
		
		Match_Struc res = results.get(0);
		assertEquals(12,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.625);
	}
	
	@Test
	public void test57(){
		source="conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
		target="conference(paper(title,document(category(day,month,year),writer(name(first,second)))))";
		
		methodCaller.getSchemas(results,source,target);
		
		Match_Struc res = results.get(0);
		assertEquals(8,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.45833333333333337);
	}
	
	@Test
	public void test58(){
		source="conference(paper(title,review(date(day,month,year),author(name(first,second)))))";
		target="event(paper(title,document(category(day,month,year),writer(name(first,second)))))";
		
		methodCaller.getSchemas(results,source,target);
		
		assertEquals(0,results.size());
	}
	
	@Test
	public void test61(){
		source="conferenceDocument(nameOfAuthor)";
		target="conferenceReview(authorName)";
		
		methodCaller.getSchemas(results,source,target);
		
		Match_Struc res = results.get(0);
		assertEquals(2,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.5);
	}
	
	@Test 
	public void test62(){
		source="conference_document(name_of_author)";
		target="conference_review(author_name)";
		
		methodCaller.getSchemas(results, source, target);
		
		Match_Struc res = results.get(0);
		assertEquals(2,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.5);
	}
	
	@Test
	public void test63(){
		source="conference_document(name_of_author)";
		target="ConferenceReview(authorName)";
		
		methodCaller.getSchemas(results, source, target);
		
		Match_Struc res = results.get(0);
		assertEquals(2,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.5);
	}
	
	@Test
	public void test64(){
		source="conference document(name of author)";
		target="conference review(author name)";
		
		methodCaller.getSchemas(results,source,target);
		
		Match_Struc res = results.get(0);
		assertEquals(2,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.5);
	}
	
	@Test
	public void test65(){
		source="conference document(nameOfAuthor)";
		target="conference review(authorName)";
		
		methodCaller.getSchemas(results,source,target);
		
		Match_Struc res = results.get(0);
		assertEquals(2,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.5);
	}
	
	@Test
	public void test66(){
		source="conferencedocument(nameofauthor)";
		target="conference review(authorname)";
		
		methodCaller.getSchemas(results, source, target);
		
		Match_Struc res = results.get(0);
		assertEquals(2,res.getNumMatches());
		assertTrue(res.getSimValue() == 0.5);
	}

}







