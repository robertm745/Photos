package model;

import java.io.Serializable;
import java.util.ArrayList;

public class PhotoList implements Serializable {

	private static final long serialVersionUID = 1L;


	private ArrayList<Photo> photos;
	
	public PhotoList() {		
		this.photos = new ArrayList<Photo>();
	}
	
	public ArrayList<Photo> getList() {
		return this.photos;
	}
	
	public void addPhoto(Photo photo) {
		this.photos.add(photo);
	}
	
	public void deletePhoto(Photo photo) {
		this.photos.remove(photo);
	}
	
	

	
}
