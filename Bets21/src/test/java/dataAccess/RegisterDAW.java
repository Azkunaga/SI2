package dataAccess;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.Date;

import org.junit.Test;

import domain.Bezero;
import domain.Pertsona;
import test.dataAccess.TestDataAccess;

public class RegisterDAW {

	// sut:system under test
	static DataAccess sut = new DataAccess(true);

	// additional operations needed to execute the test
	static TestDataAccess testDA = new TestDataAccess();

	// Jada bezeroa erregistratua dago; erabiltzaile errepikatua.
	@Test
	public void test1() {
		String erabiltzailea = "Erab1";
		String pasahitza = "123";
		String izena = "Aritz";
		String abizena = "Azkunaga";
		String nA = "72756771c";
		Date jaiotzeData = new Date(2001, 3, 7);
		int telZenb = 688812464;
		String postaElek = "aritzazkunaga@gmail.com";
		Bezero b = new Bezero(erabiltzailea, pasahitza, izena, abizena, nA, jaiotzeData, telZenb, postaElek);
		try {
			int a = testDA.register(b);
			if (a == 0) {
				int emaitza = sut.register(new Bezero("Erab1", "123", "Jon", "Jauregi", "12345678c",
						new Date(1997, 5, 3), 945677777, "jon@gmail.com"));
				assertEquals(emaitza, 1);
			} else {
				fail("Aurreko probako bezero db jarraitzen du");
			}
		} finally {
			testDA.removeBezero(b);
		}

	}

	// Badago beste bezero bat NAN berdina duena
	@Test
	public void test2() {
		String erabiltzailea = "Erab1";
		String pasahitza = "123";
		String izena = "Aritz";
		String abizena = "Azkunaga";
		String nA = "72756771c";
		Date jaiotzeData = new Date(2001, 3, 7);
		int telZenb = 688812464;
		String postaElek = "aritzazkunaga@gmail.com";
		Bezero b = new Bezero(erabiltzailea, pasahitza, izena, abizena, nA, jaiotzeData, telZenb, postaElek);
		try {
			int a = testDA.register(b);
			if (a == 0) {
				int emaitza = sut.register(new Bezero("Erab2", "123", "Jon", "Jauregi", "72756771c",
						new Date(1997, 5, 3), 945677777, "jon@gmail.com"));
				assertEquals(emaitza, 2);
			} else {
				fail("Aurreko probako bezero db jarraitzen du");
			}
		} finally {
			testDA.removeBezero(b);
		}

	}

	@Test
	public void test3() {
		String erabiltzailea = "Erab1";
		String pasahitza = "123";
		String izena = "Aritz";
		String abizena = "Azkunaga";
		String nA = "72756771c";
		Date jaiotzeData = new Date(2001, 3, 7);
		int telZenb = 688812464;
		String postaElek = "aritzazkunaga@gmail.com";
		Bezero b = new Bezero(erabiltzailea, pasahitza, izena, abizena, nA, jaiotzeData, telZenb, postaElek);
		try {
			int a = testDA.register(b);
			if (a == 0) {
				int emaitza = sut.register(new Bezero("Erab2", "123", "Jon", "Jauregi", "12345678c",
						new Date(1997, 5, 3), 945677777, "jon@gmail.com"));
				assertEquals(emaitza, 0);
			} else {
				fail("Aurreko probako bezero db jarraitzen du");
			}
		} finally {
			testDA.removeBezero(b);
		}

	}
}
