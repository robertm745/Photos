package model;

import java.io.Serializable;
import java.util.ArrayList;

public class Album implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Photo> photos;

	public Album(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	
	public void addPhoto(Photo photo) {
		this.photos.add(photo);
	}
	
	public String getName() {
		return this.name;
	}

}
