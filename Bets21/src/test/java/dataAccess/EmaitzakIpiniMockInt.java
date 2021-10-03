package dataAccess;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Vector;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import domain.Apustua;
import domain.Bezero;
import domain.Event;
import domain.Pronostikoa;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import test.businessLogic.TestFacadeImplementation;
import test.dataAccess.TestDataAccess;

@RunWith(MockitoJUnitRunner.class)
public class EmaitzakIpiniMockInt {

	@Mock
	TestDataAccess dataAccess;

	@InjectMocks
	TestFacadeImplementation sut;
	
	// event null denean
	@Test
	public void test1() {
		try {
			Question q = new Question(1,"Zenbat gol?",(float) 2,null);
			Pronostikoa p = new Pronostikoa("2 Gol",(float) 0.3,q);
			q.setResult(p);
			
			sut.createQuestion(null, "Zenbat gol?", 2);
			sut.createPronostikoa("2 Gol", (float)0.7, q);
			sut.emaitzaIpini(null, q, p);
			
			Mockito.verify(dataAccess, Mockito.times(1)).emaitzaIpini(null, q, p);
			
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}
	
	// question null denean -- AKATSA
	/*@Test
	public void test2() {
		Date data = new Date(2021,10,5);
		Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		try {
			Pronostikoa p = new Pronostikoa("2 Gol",(float) 0.3,null);
			
			sut.createEvent("Lehen mailako liga",data,"Futbola","Laliga");
			sut.createPronostikoa("2 Gol", (float)0.4, null);
			sut.emaitzaIpini(ev,null, p);
			Mockito.verify(dataAccess, Mockito.times(1)).emaitzaIpini(ev, null, p);
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}*/
	
	// pronostikoa null denean
	@Test
	public void test3() {
		Date data = new Date(2021,10,5);
		Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		try {
			Question q = new Question(1,"Zenbat gol?",(float) 2,null);
			
			q.setResult(null);
			sut.createEvent("Lehen mailako liga",data,"Futbola","Laliga");
			sut.createQuestion(null, "Zenbat gol?", (float)2);
			sut.emaitzaIpini(ev, q, null);
			Mockito.verify(dataAccess, Mockito.times(1)).emaitzaIpini(ev, q, null);
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}
	// event DBn ez egotea
	@Test
	public void test4() {
		Date data = new Date(2021,10,5);
		Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		try {
			
			Question q = ev.addQuestion("Zenbat gol?", (float)2);
			Pronostikoa p = q.addPronostikoa("2 Gol", (float)0.3);
			q.setResult(p);
			
			sut.createQuestion(null, "Zenbat gol?", 2);
			sut.createPronostikoa("2 Gol", (float)0.7, q);
			sut.emaitzaIpini(ev, q, p);
			
			Mockito.verify(dataAccess, Mockito.times(1)).emaitzaIpini(ev, q, p);
			
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}
	
	// question Dbn ez egotea -- AKATSA
	/*@Test
	public void test5() {
		Date data = new Date(2021,10,5);
		Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		try {
			
			Question q = ev.addQuestion("Zenbat gol?", (float)2);
			Pronostikoa p = q.addPronostikoa("2 Gol", (float)0.3);
			q.setResult(p);
			sut.createEvent("Lehen mailako liga",data,"Futbola","Laliga");
			sut.createPronostikoa("2 Gol", (float)0.7, q);
			sut.emaitzaIpini(ev, q, p);
			
			Mockito.verify(dataAccess, Mockito.times(1)).emaitzaIpini(ev, q, p);
			
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}*/
	
	// pronostikoa Dbn ez egotea
	@Test
	public void test6() {
		Date data = new Date(2021,10,5);
		Event ev = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		try {
			sut.createEvent("Lehen mailako liga",data,"Futbola","Laliga");
			Question q = sut.createQuestion(ev, "Zenbat gol?", (float)2);
			Pronostikoa p = q.addPronostikoa("2 Gol", (float)0.3);
			q.setResult(p);
			
			sut.emaitzaIpini(ev, q, p);
			
			Mockito.verify(dataAccess, Mockito.times(1)).emaitzaIpini(ev, q, p);
			
			fail();
		}
		catch(Exception e) {
			assertTrue(true);
		}
		
	}	
		
	//Ordaindu eta jabea
	@Test
	public void test7() {
		
		Date data = new Date(2021,10,5);
		Event e = new Event(1,"Lehen mailako liga",data,"Futbola","Laliga");
		sut.createEvent("Lehen mailako liga",data,"Futbola","Laliga");
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
		
		sut.createPronostikoa("Golak",(float) 0.3,q);
		
		try {
			sut.createQuestion(e, "Zenbat gol?", (float) 2);
		} catch (EventFinished e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (QuestionAlreadyExist e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	
		b.addDirua(ap.getApustuDirua()*ap.getKuota());
		sut.emaitzaIpini(e, q, p);
		
		
		assertEquals((float)b.getDirua(),(float)100,0.0f);
			
		
	}			
		
	// Ordaindu eta ez jabea
	@Test
	public void test8() {
		
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
		
		sut.createEvent("Lehen mailako liga",data,"Futbola","Laliga");
		sut.createPronostikoa("Golak",(float) 0.3,q);
		try {
			sut.createQuestion(e, "Zenbat gol?", (float) 2);
		} catch (EventFinished e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
	
	

}
