import static org.junit.Assert.assertEquals;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;

public class CreateQuestionDABTest {

	// sut:system under test
	static DataAccess sut = new DataAccess(true);

	// additional operations needed to execute the test
	static test.dataAccess.TestDataAccess testDA = new test.dataAccess.TestDataAccess();

	private Event ev;

	@Test
	// sut.createQuestion: The event has NOT one question with a queryText.
	public void test1() {
		try {

			// define paramaters
			String eventText = "event1";
			String queryText = "query1";
			Float betMinimum = new Float(2);
			String kirola = "kirola1";
			String txapelketa = "txapelketa1";

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;
			;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// configure the state of the system (create object in the dabatase)
			testDA.open();
			ev = testDA.addEventWithQuestion(eventText, oneDate, "query2", betMinimum, kirola, txapelketa);
			testDA.close();

			// invoke System Under Test (sut)
			Question q = sut.createQuestion(ev, queryText, betMinimum);

			// verify the results
			assertTrue(q != null);
			assertEquals(q.getQuestion(), queryText);
			assertEquals(q.getBetMinimum(), betMinimum, 0);

			// q datubasean dago
			testDA.open();
			boolean exist = testDA.existQuestion(ev, q);

			assertTrue(exist);
			testDA.close();

		} catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail
			fail();
		} finally {
			// Remove the created objects in the database (cascade removing)
			testDA.open();
			boolean b = testDA.removeEvent(ev);
			testDA.close();
			// System.out.println("Finally "+b);
		}
	}

	@Test
	// sut.createQuestion: The event is null.
	public void test2() {
		try {

			// define paramaters
			String eventText = "event1";
			String queryText = "query1";
			Float betMinimum = new Float(2);

			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = null;
			;
			try {
				oneDate = sdf.parse("05/10/2022");
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// invoke System Under Test (sut)
			Question q = sut.createQuestion(null, queryText, betMinimum);

			// verify the results
			assertTrue(q == null);

		} catch (QuestionAlreadyExist e) {
			// TODO Auto-generated catch block
			// if the program goes to this point fail
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
//	@Test
//	//sut.createQuestion:  The event has NOT one question with a queryText. 
//	public void test3() {
//		try {
//			
//			//define paramaters
//			String eventText="event1";
//			String queryText=null;
//			Float betMinimum=new Float(2);
//			String kirola="kirola1";
//			String txapelketa="txapelketa1";
//			
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Date oneDate=null;;
//			try {
//				oneDate = sdf.parse("05/10/2022");
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}	
//			
//			//configure the state of the system (create object in the dabatase)
//			testDA.open();
//			ev = testDA.addEventWithQuestion(eventText,oneDate,"query2", betMinimum, kirola, txapelketa);
//			testDA.close();			
//			
//			//invoke System Under Test (sut)  
//			Question q=sut.createQuestion(ev, queryText, betMinimum);
//			
//			
//			//verify the results
//			assertTrue(q==null);
//			
//			
//			//q datubasean dago
//			testDA.open();
//			boolean exist = testDA.existQuestion(ev,q);
//				
//			assertTrue(!exist);
//			testDA.close();
//			
//		   } catch (QuestionAlreadyExist e) {
//			// TODO Auto-generated catch block
//			// if the program goes to this point fail  
//			fail();
//			} finally {
//				  //Remove the created objects in the database (cascade removing)   
//				testDA.open();
//		          boolean b=testDA.removeEvent(ev);
//		          testDA.close();
//		      //     System.out.println("Finally "+b);          
//		        }
//		   }
}
