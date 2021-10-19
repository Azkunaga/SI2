package test.dataAccess;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import domain.Apustua;
import domain.Bezero;
import domain.Event;
import domain.Pertsona;
import domain.Pronostikoa;
import domain.Question;
import exceptions.QuestionAlreadyExist;

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
	
	public int createEvent(Event e) {
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.description=?1 AND ev.eventDate=?2",
				Event.class);
		query.setParameter(1, e.getDescription());
		query.setParameter(2, e.getEventDate());
		List<Event> l1 = query.getResultList();
		if (!l1.isEmpty())
			return 1; // gertaera hori existitzen da
		else {
			db.getTransaction().begin();
			db.persist(e);
			db.getTransaction().commit();
			return 0;
		}
	}
	
	public boolean doesEventExist(Event e) {
		Event e1 = db.find(Event.class, e.getEventNumber());
		if(e1!=null) {
			return true;
		}
		return false;
	}
	
	public void apustuaEgin(Vector<Pronostikoa> pronostikoak, float dirua, Bezero b, Bezero jabea, float kuota) {
		Pronostikoa pri;
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		Apustua a = bez.addApustua(dirua, pronostikoak, jabea, kuota);
		bez.restDirua(dirua);
		bez.addMugimendua(-(dirua), "Apustua egin", false);
		for (Pronostikoa p : pronostikoak) {
			pri = db.find(Pronostikoa.class, p.getPronostikoaNumber());
			pri.addApustua(a);
			db.getTransaction().begin();
			db.persist(pri);
			db.getTransaction().commit();
		}
		db.getTransaction().begin();
		db.persist(bez);
		db.getTransaction().commit();
	}
	
	public Bezero getBezero(Bezero b) {
		Bezero bez = db.find(Bezero.class, b.getErabiltzailea());
		if(bez!=null) {
			return bez;
		}
		return null;
	}
	
	public void deleteEvent(Event evi) {
		Event ev = db.find(Event.class, evi.getEventNumber());
		Bezero b;
		Bezero bez;
		Vector<Question> questions;
		Vector<Pronostikoa> pronostikoak;
		Vector<Apustua> apustuak;
		float dirua;
		questions = ev.getQuestions();
		for (Question qi : questions) {
			pronostikoak = qi.getPronostikoak();
			for (Pronostikoa pi : pronostikoak) {
				apustuak = pi.getApustuak();
				for (Apustua ai : apustuak) {
					dirua = ai.getApustuDirua();
					b = ai.getBezeroa();
					bez = db.find(Bezero.class, b.getErabiltzailea());
					bez.addDirua(dirua);
					bez.addMugimendua(dirua, ResourceBundle.getBundle("Etiquetas").getString("EventDeleted") + ": "
							+ ev.getDescription(), false);
					bez.removeApustua(ai);
					db.getTransaction().begin();
					db.persist(bez);
					db.getTransaction().commit();
				}
			}
		}
		db.getTransaction().begin();
		db.remove(ev);
		db.getTransaction().commit();
	}
	
	public void emaitzaIpini(Event ev, Question q, Pronostikoa pi) {
		boolean ordaindu = true;
		Question qi = db.find(Question.class, q.getQuestionNumber());
		Pronostikoa p = db.find(Pronostikoa.class, pi.getPronostikoaNumber());
		qi.setResult(p);
		db.getTransaction().begin();
		db.persist(qi);
		db.getTransaction().commit();
		Vector<Apustua> ap = p.getApustuak();
		for (Apustua api : ap) {
			Bezero b = db.find(Bezero.class, api.getBezeroa().getErabiltzailea());
			Float kuota = api.getKuota();
			Float apustuDirua = api.getApustuDirua();
			Vector<Pronostikoa> pronostikoak = api.getPronostikoak();
			for (Pronostikoa pr : pronostikoak) {
				Question qr = pr.getQ();
				if (!pr.equals(qr.getResult())) {
					ordaindu = false;
				}
			}
			if (ordaindu) {
				b.addDirua(kuota * apustuDirua);
				b.addMugimendua(kuota * apustuDirua, ResourceBundle.getBundle("Etiquetas").getString("Win"), false);
				db.getTransaction().begin();
				db.persist(b);
				db.getTransaction().commit();
				Bezero jabea = api.getJabea();
				if (jabea != null) {
					Bezero aur = db.find(Bezero.class, jabea.getErabiltzailea());
					aur.addDirua((float) (kuota * apustuDirua * 0.1));
					aur.addMugimendua((float) (kuota * apustuDirua * 0.1),
							ResourceBundle.getBundle("Etiquetas").getString("WinThanksTo") + b.getErabiltzailea(),
							true);
					db.getTransaction().begin();
					db.persist(aur);
					db.getTransaction().commit();
				}

			}
		}

	}
	
	public int createPronostikoa(String pronostikoa, float kuota, Question qi) {
		Question q = db.find(Question.class, qi.getQuestionNumber());
		if (q == null) {
			return 0;
		}
		if (!q.doesPronostikoaExist(pronostikoa)) {
			q.addPronostikoa(pronostikoa, kuota);
			db.getTransaction().begin();
			db.persist(q);
			db.getTransaction().commit();
			return 0;
		} else {
			return 1;
		}
	}
	
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.doesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}
	
	public Pertsona isLogin(String erabiltzailea, String pasahitza) {
		TypedQuery<Pertsona> query = db
				.createQuery("SELECT p FROM Pertsona p WHERE p.erabiltzailea=?1 AND p.pasahitza=?2", Pertsona.class);
		query.setParameter(1, erabiltzailea);
		query.setParameter(2, pasahitza);
		List<Pertsona> p = query.getResultList();
		if (p.isEmpty())
			return null;
		else
			return p.get(0);
	}

}
