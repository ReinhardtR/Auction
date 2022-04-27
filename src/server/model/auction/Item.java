package server.model.auction;

import java.io.Serializable;

public class Item implements Serializable {
	private final String id;

	public Item(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}
}
