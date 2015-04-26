package controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.Message;

public class MessageServer implements Iterable<Message> {
	private Map<Integer, List<Message>> messages;

	private List<Message> selected;

	public MessageServer() {
		selected = new ArrayList<Message>();
		messages = loadMessage();

	}

	private Map<Integer, List<Message>> loadMessage() {
		Map<Integer, List<Message>> result = new TreeMap<Integer, List<Message>>();
		List<Message> list = new ArrayList<Message>();
		list.add(new Message("title1", "message 1 from server 0"));
		list.add(new Message("title2", "message 2 from server 0"));
		result.put(0, list);

		list = new ArrayList<Message>();
		list.add(new Message("title1", "message 1 from server 1"));
		list.add(new Message("title2", "message 2 from server 1"));
		list.add(new Message("title3", "message 3 from server 1"));
		result.put(1, list);
		list = new ArrayList<Message>();
		list.add(new Message("title1", "message 1 from server 2"));
		// list.add(new Message("title2", "message 2 from server 2"));
		result.put(2, list);
		return result;
	}

	public void setSelectedServer(Set<Integer> server) {
		selected.clear();
		for (Integer id : server) {
			if (messages.containsKey(id)) {
				selected.addAll(messages.get(id));
			}
		}
	}

	public int getMessageCount() {
		return selected.size();
	}

	@Override
	public Iterator<Message> iterator() {
		return new MessageIterator(selected);
	}
}

class MessageIterator implements Iterator<Message> {
	private Iterator<Message> iterator;

	public MessageIterator(List<Message> messages) {
		iterator = messages.iterator();
	}

	@Override
	public boolean hasNext() {
		return iterator.hasNext();
	}

	@Override
	public Message next() {
		try {
			Thread.sleep(2 * 1000);
		} catch (InterruptedException e) {
		}
		return iterator.next();
	}

	@Override
	public void remove() {
		iterator.remove();
	}

}
