package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

public class Album implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String name;
	private ArrayList<Photo> photos;
	private ArrayList<String> paths;
	private String dateRange;
	
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
		this.paths = new ArrayList<String>();
		this.dateRange = "";
	}
	
	public ArrayList<Photo> getPhotos() {
		return this.photos;
	}
	
	public ArrayList<String> getPaths() {
		return this.paths;
	}
	
	public void addPhoto(Photo photo) {
		this.photos.add(photo);
		this.paths.add(photo.toString());
		this.setDateRange();
	}
	
	public void deletePhoto(Photo photo) {
		this.photos.remove(photo);
		this.paths.remove(photo.toString());
		this.setDateRange();
	}
	
	public Photo getPhoto(String path) {
		for (Photo p : photos) {
			if (p.toString().equalsIgnoreCase(path))
				return p;
		}
		return null;
	}
	
	public void rename(String name) {
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Album))
			return false;
		Album temp = (Album) o;
		return this.toString().equalsIgnoreCase(temp.toString());
	}

	
	public int compareTo(Album a) {
			return this.toString().compareTo(a.toString());
	}

	public int getSize() {
		return this.photos.size();
	}
	
	public void setDateRange() {
		Calendar earliest = null;
		Calendar latest = null;
		if (this.photos.size() == 0) {
			this.dateRange = "";
		} else if (this.photos.size() == 1) {
			this.dateRange = this.photos.get(0).getDateTime().getTime().toString();
		}
		else {
			earliest = this.photos.get(0).getDateTime();
			latest = this.photos.get(0).getDateTime();
			for (Photo p : this.photos) {
				if (p.getDateTime().after(latest))
					latest = p.getDateTime();
				else if (p.getDateTime().before(earliest))
					earliest = p.getDateTime();
			}
			this.dateRange = latest.getTime().toString() + " - " + earliest.getTime().toString();
		}
	}
	
	public String getDateRange() {
		return this.dateRange;
	}
	
}
