package dataAccess;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

import org.junit.Test;

import configuration.UtilDate;
import domain.Bezero;
import domain.Event;
import domain.Pertsona;
import domain.Pronostikoa;
import domain.Question;
import test.dataAccess.TestDataAccess;

public class DeleteEventDAB {

	// sut:system under test
	static DataAccess sut = new DataAccess(true);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	// Lehenengo eta bigarren for-ean sartu eta gertaera borratu. 1-3 BK
	@Test
	public void test1() {
		Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(2021, 10, 17), "Soccer", "La Liga");
		Question q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
		Pronostikoa p1 = q1.addPronostikoa("1", (float) 1.5);
		Pronostikoa p2 = q1.addPronostikoa("2", (float) 2.2);
		try {
			testDA.open();
			testDA.createEvent(ev1);
			assertTrue(testDA.doesEventExist(ev1));
			testDA.close();

			sut.deleteEvent(ev1);

			testDA.open();
			// DB-n existitzen ez dela frogatu
			assertFalse(testDA.doesEventExist(ev1));
			testDA.close();

		} catch (Exception e) {
			fail();
		} finally {

		}

	}

	// Lehenengo eta bigarren for-ean sartu, gertaera borratu eta dirua itzuli.
	// 1-3-5 BK
	@Test
	public void test2() {
		Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(2021, 10, 17), "Soccer", "La Liga");
		Question q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
		Pronostikoa p1 = q1.addPronostikoa("1", (float) 1.5);
		Pronostikoa p2 = q1.addPronostikoa("2", (float) 2.2);
		Bezero bezero = new Bezero("Erab2", "123", "Jon", "Jauregi", "12345678c", new Date(1997, 5, 3), 945677777,
				"jon@gmail.com");
		bezero.addDirua(10);
		Vector<Pronostikoa> lista = new Vector<Pronostikoa>();
		lista.add(p1);
		try {
			testDA.open();
			testDA.register(bezero);
			testDA.createEvent(ev1);
			testDA.apustuaEgin(lista, 2, bezero, null, p1.getKuota());
			assertTrue(testDA.doesEventExist(ev1));
			testDA.close();

			sut.deleteEvent(ev1);

			testDA.open();
			// DB-n existitzen ez dela frogatu
			assertFalse(testDA.doesEventExist(ev1));
			// hasierako dirua
			assertTrue(10 == testDA.getBezero(bezero).getDirua());
			testDA.close();

		} catch (Exception e) {
			fail();
		} finally {
			testDA.open();
			testDA.removeBezero(bezero);
			testDA.close();
		}

	}

	// Event objektuaren ordez null jarrita
	@Test
	public void test3() {
		try {
			sut.deleteEvent(null);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		} finally {

		}

	}
	
	@Test
	public void test4() {
		Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(2021, 10, 17), "Soccer", "La Liga");
		Question q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
		Pronostikoa p1 = q1.addPronostikoa("1", (float) 1.5);
		Pronostikoa p2 = q1.addPronostikoa("2", (float) 2.2);
		try {
	
			testDA.open();
			// DB-n existitzen ez dela frogatu
			assertFalse(testDA.doesEventExist(ev1));
			testDA.close();
			
			sut.deleteEvent(ev1);

			fail();

		} catch (Exception e) {
			assertTrue(true);
		} finally {

		}

	}

}
