package test.dataAccess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Bezero;
import domain.Event;
import domain.Pertsona;
import domain.Question;

public class TestDataAccess {
	protected EntityManager db;
	protected EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public TestDataAccess() {

		System.out.println("Creating TestDataAccess instance");

		open();

	}

	public void open() {

		System.out.println("Opening TestDataAccess instance ");

		String fileName = c.getDbFilename();

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public boolean removeEvent(Event ev) {
		System.out.println(">> DataAccessTest: removeEvent");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			db.getTransaction().begin();
			db.remove(e);
			db.getTransaction().commit();
			return true;
		} else
			return false;
	}

	public Event addEventWithQuestion(String desc, Date d, String question, float qty, String kirola,
			String txapelketa) {
		System.out.println(">> DataAccessTest: addEvent");
		Event ev = null;
		db.getTransaction().begin();
		try {
			ev = new Event(desc, d, kirola, txapelketa);
			ev.addQuestion(question, qty);
			db.persist(ev);
			db.getTransaction().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ev;
	}

	public boolean existQuestion(Event ev, Question q) {
		System.out.println(">> DataAccessTest: existQuestion");
		Event e = db.find(Event.class, ev.getEventNumber());
		if (e != null) {
			return e.doesQuestionExists(q.getQuestion());
		} else
			return false;
	}

	public int register(Bezero p) {
		TypedQuery<Pertsona> query = db.createQuery("SELECT p FROM Pertsona p WHERE p.erabiltzailea=?1",
				Pertsona.class);
		query.setParameter(1, p.getErabiltzailea());
		List<Pertsona> l1 = query.getResultList();
		if (!l1.isEmpty())
			return 1; // erabiltzaile hori existitzen da
		else {
			TypedQuery<Bezero> query2 = db.createQuery("SELECT b FROM Bezero b WHERE b.NA=?1", Bezero.class);
			query2.setParameter(1, p.getNA());
			List<Bezero> l = query2.getResultList();
			if (!l.isEmpty()) {
				return 2; // NA hori existitzen da
			} else {
				db.getTransaction().begin();
				db.persist(p);
				db.getTransaction().commit();
				return 0;
			}
		}
	}
	
	public void removeBezero(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		if(bez!=null) {
			db.getTransaction().begin();
			db.remove(bez);
			db.getTransaction().commit();
		}
	}

}
