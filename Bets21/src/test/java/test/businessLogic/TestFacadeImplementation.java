package test.businessLogic;

import java.util.Date;

import javax.jws.WebMethod;

import configuration.ConfigXML;
import domain.Bezero;
import domain.Event;
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

}
