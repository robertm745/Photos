package model;

import java.io.Serializable;
import java.util.ArrayList;

public class PhotoList implements Serializable {

	private static final long serialVersionUID = 1L;


	private ArrayList<Photo> photos;
	private ArrayList<String> paths;
	
	public PhotoList() {		
		this.photos = new ArrayList<Photo>();
		this.paths = new ArrayList<String>();
	}
	
	public ArrayList<Photo> getList() {
		return this.photos;
	}
	
	public ArrayList<String> getPaths() {
		return this.paths;
	}
	
	public void addPhoto(Photo photo) {
		this.photos.add(photo);
		this.paths.add(photo.toString());
	}
	
	public void deletePhoto(Photo photo) {
		this.photos.remove(photo);
		this.paths.remove(photo.toString());
	}
	
	public Photo getPhoto(String path) {
		for (Photo p : photos) {
			if (p.toString().equalsIgnoreCase(path))
				return p;
		}
		return null;
	}
	


	
}
