package dataAccess;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import businessLogic.BLFacadeImplementation;
import configuration.UtilDate;
import domain.Bezero;
import domain.Event;
import domain.Pertsona;
import domain.Pronostikoa;
import domain.Question;
import test.businessLogic.TestFacadeImplementation;
import test.dataAccess.TestDataAccess;

@RunWith(MockitoJUnitRunner.class)
public class DeleteEventMockInt {

	@Mock
	TestDataAccess dataAccess;

	@InjectMocks
	TestFacadeImplementation sut;

	/*
	 * // null den Bezeroa sartzen da. AKATSA
	 * 
	 * @Test public void test1() { try { sut.removeEvent(null); fail(); } catch
	 * (Exception e) { assertTrue(true); } }
	 */

	// Gertaera ezabatzen dela egiaztatu.
	@Test
	public void test2() {
		Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(2021, 10, 17), "Soccer", "La Liga");
		Question q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
		Pronostikoa p1 = q1.addPronostikoa("1", (float) 1.5);
		Pronostikoa p2 = q1.addPronostikoa("2", (float) 2.2);
		try {
			sut.deleteEvent(ev1);

			Mockito.verify(dataAccess, Mockito.times(1)).deleteEvent(Mockito.any(Event.class));
		} catch (Exception e) {
			fail();
		}
	}

}
