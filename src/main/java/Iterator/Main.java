package Iterator;

import java.util.Date;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import domain.Event;

public class Main {

	public static void main(String[] args) {
		
		BLFacade facade = new BLFacadeImplementation();
		
		EventIterator<Event> events = (EventIterator)facade.getEvents(new Date("2021/12/17"), "Futbola", "LaLiga");
		
		// Bukaeratik
		events.goLast();
		while (events.hasPrevious()) {
			Event event = (Event) events.previous();
			System.out.println(event.toString());
			
		}
		
		// Hasieratik
		events.goFirst();
		while (events.hasNext()) {
			Event event = (Event) events.next();
			System.out.println(event.toString());
			
		}
		
	}

}
