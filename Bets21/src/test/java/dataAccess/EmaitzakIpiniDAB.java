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

	// parametroak null direnean
	@Test
	public void test1() {
		try {
			sut.emaitzaIpini(null, null, null);
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}
	
	//parametroak DBen ez egotea
	@Test
	public void test2() {
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
			
			sut.emaitzaIpini(e, q, p);
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}
	
	//Ordaindu eta jabea
	@Test
	public void test4() {
		
		Date data = new Date(2021,10,5);
		Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		Question q = new Question(1,"Zenbat gol?",(float) 2,e);
		Pronostikoa p = new Pronostikoa("3 Gol",(float) 0.3,q);
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
			
			assertEquals(b.getDirua(),97);
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
	public void test5() {
		
		Date data = new Date(2021,10,5);
		Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		Question q = new Question(1,"Zenbat gol?",(float) 2,e);
		Pronostikoa p = new Pronostikoa("3 Gol",(float) 0.3,q);
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
			
			assertEquals(b2.getDirua(),107);
			assertEquals(b.getDirua(),19);
		}
		
		
		finally {
			testDA.open();
			testDA.removeBezero(b);
			testDA.removeEvent(e);
			testDA.close();
		}
	}
	

}
