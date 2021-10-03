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

public class EmaitzakIpiniDAW {

	// sut:system under test
	static DataAccess sut = new DataAccess(true);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	// Apusturik ez dagoenean
	@Test
	public void test1() {
		
		Date data = new Date(2021,10,5);
		Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		Question q = new Question(1,"Zenbat gol?",(float) 2,e);
		Pronostikoa p = new Pronostikoa("2 gol",(float) 0.3,q);
		
		try {
			testDA.open();
			sut.createEvent(e);
			sut.createPronostikoa("2 Gol",(float) 0.3,q);
			try {
				sut.createQuestion(e, "Zenbat gol?", (float) 2);
			} catch (QuestionAlreadyExist e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sut.emaitzaIpini(e, q, p);
		}
		
		
		finally {
			
		}
	}
	
	// Apustua ez ordaindu
	@Test
	public void test2() {
		
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
		pros.addElement(p);
		Apustua ap = new Apustua((float)3,pros,b,b,30);
		try {
			testDA.open();
			int a = testDA.register(b);
			sut.createEvent(e);
			sut.createPronostikoa("2 Gol",(float) 0.3,q);
			try {
				sut.createQuestion(e, "Zenbat gol?", (float) 2);
			} catch (QuestionAlreadyExist e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sut.emaitzaIpini(e, q, p);
			
			assertEquals(b.getDirua(),10);
		}
		
		
		finally {
			testDA.open();
			testDA.removeBezero(b);
			testDA.removeEvent(e);
			testDA.close();
		}
	}
	
	// Apustua ordaindu
	@Test
	public void test3() {
		
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
		pros.addElement(p);
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
			
			assertEquals(b.getDirua(),10);
		}
		
		
		finally {
			testDA.open();
			testDA.removeBezero(b);
			testDA.removeEvent(e);
			testDA.close();
		}
	}

}
