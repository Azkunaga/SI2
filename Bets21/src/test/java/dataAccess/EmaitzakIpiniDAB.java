package dataAccess;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import domain.Apustua;
import domain.Bezero;
import domain.Event;
import domain.Pronostikoa;
import domain.Question;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDAB {

	// sut:system under test
	static DataAccess sut = new DataAccess(true);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	// event null denean
	@Test
	public void test1() {
		try {
			Question q = new Question(1,"Zenbat gol?",(float) 2,null);
			Pronostikoa p = new Pronostikoa("2 Gol",(float) 0.3,q);
			q.setResult(p);
			sut.createQuestion(null, "Zenbat gol?", (float)2);
			sut.createPronostikoa("2 Gol", (float)0.4, q);
			sut.emaitzaIpini(null, q, p);
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}
	
	// question null denean
	@Test
	public void test2() {
		Date data = new Date(2021,10,5);
		Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		try {
			Pronostikoa p = new Pronostikoa("2 Gol",(float) 0.3,null);
			
			testDA.open();
			testDA.createEvent(ev);
			testDA.close();
			sut.createQuestion(null, "Zenbat gol?", (float)2);
			sut.createPronostikoa("2 Gol", (float)0.4, null);
			sut.emaitzaIpini(ev,null, p);
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		finally {
			testDA.open();
			testDA.removeEvent(ev);
			testDA.close();
		}
		
	}
	// pronostikoa null denean
		@Test
		public void test3() {
			Date data = new Date(2021,10,5);
			Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
			try {
				Question q = new Question(1,"Zenbat gol?",(float) 2,null);
				
				q.setResult(null);
				
				testDA.open();
				testDA.createEvent(ev);
				testDA.close();
				sut.createQuestion(null, "Zenbat gol?", (float)2);
				sut.createPronostikoa("2 Gol", (float)0.4, q);
				sut.emaitzaIpini(ev, q, null);
				fail();
			}
			catch(Exception e) {
				assertTrue(true);
			}
			finally {
				testDA.open();
				testDA.removeEvent(ev);
				testDA.close();
			}
			
		}
		//event DBen ez egotea
		@Test
		public void test4() {
			try {
				Date data = new Date(2021,10,5);
				Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
				Question q = new Question(1,"Zenbat gol?",(float) 2,e);
				Pronostikoa p = new Pronostikoa("2 Gol",(float) 0.3,q);
				q.setResult(p);
				Pronostikoa pEzIrabazi = new Pronostikoa("3 Gol",(float) 0.3,q);
				Bezero b = new Bezero("Erab1", "123", "Jon", "Jauregi", "12345678c", new Date(1997, 5, 3),
						945677777, "jon@gmail.com");
				b.setDirua(10);
				Vector<Pronostikoa> pros = new Vector<Pronostikoa>();
				pros.addElement(pEzIrabazi);
				Apustua ap = new Apustua((float)3,pros,b,b,30);
				
				sut.createQuestion(e, "Zenbat gol?", 2);
				sut.createPronostikoa("2 Gol", (float)0.3, q);
				sut.emaitzaIpini(e, q, p);
				fail();
			}
			catch(Exception e) {
				assertTrue(true);
			}
			
		}
	
	//question DBen ez egotea
	@Test
	public void test5() {
		Date data = new Date(2021,10,5);
		Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		try {
			testDA.open();
			testDA.createEvent(ev);
			testDA.close();
			Question q = new Question(1,"Zenbat gol?",(float) 2,ev);
			Pronostikoa p = new Pronostikoa("2 Gol",(float) 0.3,q);
			q.setResult(p);
			Pronostikoa pEzIrabazi = new Pronostikoa("3 Gol",(float) 0.3,q);
			Bezero b = new Bezero("Erab1", "123", "Jon", "Jauregi", "12345678c", new Date(1997, 5, 3),
					945677777, "jon@gmail.com");
			b.setDirua(10);
			Vector<Pronostikoa> pros = new Vector<Pronostikoa>();
			pros.addElement(pEzIrabazi);
			Apustua ap = new Apustua((float)3,pros,b,b,30);
			
			sut.createPronostikoa("Zenbat gol?",(float) 2, q);
			sut.emaitzaIpini(ev, q, p);
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		finally {
			
		}
		
	}
	
	//parametroak DBen ez egotea
		@Test
		public void test6() {
			
			Date data = new Date(2021,10,5);
			Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
			try {
				testDA.open();
				testDA.createEvent(ev);
				testDA.close();
				Question q = new Question(1,"Zenbat gol?",(float) 2,ev);
				Pronostikoa p = new Pronostikoa("2 Gol",(float) 0.3,q);
				q.setResult(p);
				Pronostikoa pEzIrabazi = new Pronostikoa("3 Gol",(float) 0.3,q);
				Bezero b = new Bezero("Erab1", "123", "Jon", "Jauregi", "12345678c", new Date(1997, 5, 3),
						945677777, "jon@gmail.com");
				b.setDirua(10);
				Vector<Pronostikoa> pros = new Vector<Pronostikoa>();
				pros.addElement(pEzIrabazi);
				Apustua ap = new Apustua((float)3,pros,b,b,30);
				
				sut.emaitzaIpini(ev, q, p);
				fail();
			}
			catch(Exception e) {
				assertTrue(true);
			}
			finally {
				testDA.open();
				testDA.removeEvent(ev);
				testDA.close();
			}
			
		}
	
	//Ordaindu eta jabea
	@Test
	public void test7() {
		
		Date data = new Date(2021,10,5);
		Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		Question q = e.addQuestion("Zenbat gol?",(float) 2);
		Pronostikoa p = q.addPronostikoa("3 Gol",(float) 0.3);
		q.setResult(p);
		Pronostikoa pIrabazi = new Pronostikoa("3 Gol",(float) 0.3,q);
		Bezero b = new Bezero("Erab1", "123", "Jon", "Jauregi", "12345678c", new Date(1997, 5, 3),
				945677777, "jon@gmail.com");
		b.setDirua(10);
		Vector<Pronostikoa> pros = new Vector<Pronostikoa>();
		pros.addElement(pIrabazi);
		Apustua ap = new Apustua((float)3,pros,b,b,30);
		try {
			testDA.open();
			int a = testDA.register(b);
			sut.createEvent(e);
			sut.createPronostikoa("Golak",(float) 0.3,q);
			try {
				sut.createQuestion(e, "Zenbat gol?", (float) 2);
			} catch (QuestionAlreadyExist e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			b.addDirua(ap.getApustuDirua()*ap.getKuota());
			sut.emaitzaIpini(e, q, p);
			
			assertEquals(b.getDirua(),100,0.0f);
		}
		
		
		finally {
			testDA.open();
			testDA.removeBezero(b);
			testDA.removeEvent(e);
			testDA.close();
		}
	}
	
	//Ez ordaindu
		@Test
		public void test8() {
			
			Date data = new Date(2021,10,5);
			Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
			Question q = e.addQuestion("Zenbat gol?",(float) 2);
			Pronostikoa p = q.addPronostikoa("3 Gol",(float) 0.3);
			q.setResult(p);
			Pronostikoa pIrabazi = new Pronostikoa("2 Gol",(float) 0.3,q);
			Bezero b = new Bezero("Erab1", "123", "Jon", "Jauregi", "12345678c", new Date(1997, 5, 3),
					945677777, "jon@gmail.com");
			b.setDirua(10);
			Vector<Pronostikoa> pros = new Vector<Pronostikoa>();
			pros.addElement(pIrabazi);
			Apustua ap = new Apustua((float)3,pros,b,b,30);
			try {
				testDA.open();
				int a = testDA.register(b);
				sut.createEvent(e);
				sut.createPronostikoa("Golak",(float) 0.3,q);
				try {
					sut.createQuestion(e, "Zenbat gol?", (float) 2);
				} catch (QuestionAlreadyExist e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				sut.emaitzaIpini(e, q, p);
				
				assertEquals(b.getDirua(),10,0.0f);
			}
			
			
			finally {
				testDA.open();
				testDA.removeBezero(b);
				testDA.removeEvent(e);
				testDA.close();
			}
		}
	
	// Ordaindu eta ez jabea
	
	@Test
	public void test9() {
		
		Date data = new Date(2021,10,5);
		Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		Question q = e.addQuestion("Zenbat gol?",(float) 2);
		Pronostikoa p = q.addPronostikoa("3 Gol",(float) 0.3);
		q.setResult(p);
		Pronostikoa pIrabazi = new Pronostikoa("3 Gol",(float) 0.3,q);
		Bezero b = new Bezero("Erab1", "123", "Jon", "Jauregi", "12345678c", new Date(1997, 5, 3),
				945677777, "jon@gmail.com");
		b.setDirua(10);
		Bezero b2 = new Bezero("Erab2", "123", "Aritz", "Azkunaga", "12345678c", new Date(1997, 5, 3),
				945677777, "aritzn@gmail.com");
		b2.setDirua(20);
		Vector<Pronostikoa> pros = new Vector<Pronostikoa>();
		pros.addElement(pIrabazi);
		Apustua ap = new Apustua((float)3,pros,b2,b,30);
		try {
			testDA.open();
			int a = testDA.register(b);
			sut.createEvent(e);
			sut.createPronostikoa("Golak",(float) 0.3,q);
			try {
				sut.createQuestion(e, "Zenbat gol?", (float) 2);
			} catch (QuestionAlreadyExist e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			b2.addDirua(ap.getApustuDirua()*ap.getKuota());
			b.addDirua((float)(ap.getApustuDirua()*ap.getKuota()*0.1));
			sut.emaitzaIpini(e, q, p);
			
			assertEquals(b2.getDirua(),110,0.0f);
			assertEquals(b.getDirua(),19,0.0f);
		}
		
		
		finally {
			testDA.open();
			testDA.removeBezero(b);
			testDA.removeEvent(e);
			testDA.close();
		}
	}
	

}
