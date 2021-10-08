package dataAccess;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import businessLogic.BLFacadeImplementation;
import domain.Bezero;
import domain.Event;
import domain.Pertsona;
import test.businessLogic.TestFacadeImplementation;
import test.dataAccess.TestDataAccess;

@RunWith(MockitoJUnitRunner.class)
public class RegisterMockIntTest {

	@Mock
	TestDataAccess dataAccess;

	@InjectMocks
	TestFacadeImplementation sut;

	/*
	 // null den Bezeroa sartzen da. AKATSA
	@Test
	public void test1() {	
		try {
			int a = sut.register(null);
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}
	*/

	// Jada bezeroa erregistratua dago; erabiltzaile errepikatua.
	@Test
	public void test2() {
		Mockito.doReturn(1).when(dataAccess).register(Mockito.any(Bezero.class));

		int emaitza = sut.register(new Bezero("Erab1", "123", "Aritz", "Azkunaga", "72756771c", new Date(2001, 3, 7),
				688812464, "aritzazkunaga@gmail.com"));

		assertEquals(1, emaitza);

	}	

	// Badago beste bezero bat NAN berdina duena
	@Test
	public void test3() {

		Mockito.doReturn(2).when(dataAccess).register(Mockito.any(Bezero.class));

		int emaitza = sut.register(new Bezero("Erab1", "123", "Aritz", "Azkunaga", "72756771c", new Date(2001, 3, 7),
				688812464, "aritzazkunaga@gmail.com"));

		assertEquals(2, emaitza);

	}

	// Dena ondo joan bada
	@Test
	public void test4() {
		Mockito.doReturn(0).when(dataAccess).register(Mockito.any(Bezero.class));

		int emaitza = sut.register(new Bezero("Erab1", "123", "Aritz", "Azkunaga", "72756771c", new Date(2001, 3, 7),
				688812464, "aritzazkunaga@gmail.com"));

		assertEquals(0, emaitza);
	}

}
