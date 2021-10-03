package test.businessLogic;

import java.util.Date;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.jws.WebMethod;

import configuration.ConfigXML;
import domain.Bezero;
import domain.Event;
import domain.Pronostikoa;
import domain.Question;
import exceptions.EventFinished;
import exceptions.QuestionAlreadyExist;
import test.dataAccess.TestDataAccess;

public class TestFacadeImplementation {
	TestDataAccess dbManagerTest;

	public TestFacadeImplementation() {
		System.out.println("Creating TestFacadeImplementation instance");
		ConfigXML c = ConfigXML.getInstance();
		dbManagerTest = new TestDataAccess();
		dbManagerTest.close();
	}

	public boolean removeEvent(Event ev) {
		dbManagerTest.open();
		boolean b = dbManagerTest.removeEvent(ev);
		dbManagerTest.close();
		return b;

	}

	public Event addEventWithQuestion(String desc, Date d, String q, float qty, String kirola, String txapelketa) {
		dbManagerTest.open();
		Event o = dbManagerTest.addEventWithQuestion(desc, d, q, qty, kirola, txapelketa);
		dbManagerTest.close();
		return o;
	}

	public int register(Bezero p) {
		dbManagerTest.open();
		int n = dbManagerTest.register(p);
		dbManagerTest.close();
		return n;
	}
	
	public void apustuaEgin(Vector<Pronostikoa> pronostikoak, float dirua, Bezero b, Bezero jabea, float kuota) {
		dbManagerTest.open();
		dbManagerTest.apustuaEgin(pronostikoak, dirua, b, jabea, kuota);
		dbManagerTest.close();
	}
	
	public void deleteEvent(Event evi) {
		dbManagerTest.open();
		dbManagerTest.deleteEvent(evi);
		dbManagerTest.close();
	}
	
	public void emaitzaIpini(Event ev, Question q, Pronostikoa pi) {
		dbManagerTest.open();
		dbManagerTest.emaitzaIpini(ev, q, pi);
		dbManagerTest.close();

	}
	public int createEvent(String description, Date eventDate, String kirola, String txapelketa) {
		dbManagerTest.open();
		Event e = new Event(description, eventDate, kirola, txapelketa);
		int n = dbManagerTest.createEvent(e);
		dbManagerTest.close();
		return n;
	}
	
	public int createPronostikoa(String pronostikoa, float kuota, Question qi) {
		dbManagerTest.open();
		int n = dbManagerTest.createPronostikoa(pronostikoa, kuota, qi);
		dbManagerTest.close();
		return n;
	}
	
	public Question createQuestion(Event event, String question, float betMinimum)
			throws EventFinished, QuestionAlreadyExist {

		// The minimum bed must be greater than 0
		dbManagerTest.open();
		Question qry = null;

		if (new Date().compareTo(event.getEventDate()) > 0)
			throw new EventFinished(ResourceBundle.getBundle("Etiquetas").getString("ErrorEventHasFinished"));

		qry = dbManagerTest.createQuestion(event, question, betMinimum);

		dbManagerTest.close();

		return qry;
	}

}
