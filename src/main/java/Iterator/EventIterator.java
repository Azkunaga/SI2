package Iterator;

import java.util.List;
import java.util.Vector;

import domain.Event;

public class EventIterator<Event> implements ExtendedIterator<Event>{

	Vector<Event> listEvent;
	int position = 0;
	
	public EventIterator(Vector<Event> e) {
		
		listEvent = e;
		
	}
	
	public Vector<Event> getListEvent() {
		return this.listEvent;
	}
	@Override
	public boolean hasNext() {
		
		return position < listEvent.size();
	}

	@Override
	public Event next() {
		
		Event e = listEvent.get(position);
		position++;
		return e;
	}

	@Override
	public Event previous() {
		
		Event e = listEvent.get(position);
		position--;
		return e;
	}

	@Override
	public boolean hasPrevious() {
		
		return position >= 0;
	}

	@Override
	public void goFirst() {
		position = 0;
	}

	@Override
	public void goLast() {
		position = listEvent.size();
		
	}

}
