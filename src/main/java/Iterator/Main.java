package Iterator;

import java.net.MalformedURLException;
import java.util.Date;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import configuration.ConfigXML;
import domain.Event;
import gui.ApplicationLauncherFactory;

public class Main {

	public static void main(String[] args) {
		
		ConfigXML c=ConfigXML.getInstance();
		ApplicationLauncherFactory apf= new ApplicationLauncherFactory(); 
		BLFacade appFacadeInterface;
		try {
			appFacadeInterface = apf.createAL(c);
			EventIterator<Event> events = (EventIterator)appFacadeInterface.getEvents(new Date("2021/12/17"), "Futbola", "LaLiga");
			
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
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
	}

}
