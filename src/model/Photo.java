package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class Photo implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String location;
	private HashMap<String, ArrayList<String>> tags;
	private String caption;

	public Photo(String location) {
		// TODO Auto-generated constructor stub
		this.location = location;
		this.tags = new HashMap<>();
		this.location = location;
	}

	public void addTag(String key, String value) {
		if (tags.containsKey(key)) {
			tags.get(key).add(value);
		} else {

		}
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getLocation() {
		return location;
	}

}
