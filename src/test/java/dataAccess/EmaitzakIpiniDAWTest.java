package dataAccess;



import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;

import dataAccess.DataAccess;
import domain.Apustua;
import domain.Bezero;
import domain.Event;
import domain.Pronostikoa;
import domain.Question;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class EmaitzakIpiniDAWTest {

	// sut:system under test
	static DataAccess sut = new DataAccess(true);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	// Apusturik ez dagoenean
	@Test
	public void test1() {
		
		Date data = new Date(2021,10,5);
		Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		Question q = e.addQuestion("Zenbat gol?", (float) 2);
		Pronostikoa p = q.addPronostikoa("2 gol",(float) 0.3);
		
		try {
			testDA.open();
			testDA.createEvent(e);
			testDA.close();
			
			sut.createPronostikoa("2 Gol",(float) 0.3,e.getQuestions().firstElement());
			try {
				sut.createQuestion(e, "Zenbat gol?", (float) 2);
			} catch (QuestionAlreadyExist e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			sut.emaitzaIpini(e, e.getQuestions().firstElement(), p);
		}
		
		
		finally {
			testDA.open();
			testDA.removeEvent(e);
			testDA.close();
		}
	}
	
	// Apustua ez ordaindu
	@Test
	public void test2() {
		
		Date data = new Date(2021,10,5);
		Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		Question q = e.addQuestion("Zenbat gol?",(float) 2);
		Pronostikoa p = q.addPronostikoa("2 Gol",(float) 0.3);
		q.setResult(p);
		Pronostikoa pEzIrabazi = new Pronostikoa("3 Gol",(float) 0.3,q);
		Bezero b = new Bezero("Erab1", "123", "Jon", "Jauregi", "12345678c", new Date(1997, 5, 3),
				945677777, "jon@gmail.com");
		b.setDirua(10);
		Vector<Pronostikoa> pros = new Vector<Pronostikoa>();
		pros.addElement(pEzIrabazi);
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
			
			assertEquals(b.getDirua(),10,0.0f);
		}
		
		
		finally {
			testDA.open();
			testDA.removeBezero(b);
			testDA.removeEvent(e);
			testDA.close();
		}
	}
	
	// Apustua ordaindu eta jabea
	@Test
	public void test3() {
		
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
	
	// ordaindu eta ez jabea
	@Test
	public void test4() {
		
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
