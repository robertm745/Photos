package model;

import java.io.Serializable;

public class Album implements Serializable {

	
	private static final long serialVersionUID = 1L;
	private String name;
	private PhotoList photoList;
	private String dateRange;
	
	public Album(String name) {
		this.name = name;
		this.photoList = new PhotoList();
	}
	
	public PhotoList getPhotoList() {
		return this.photoList;
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
		return this.photoList.getList().size();
	}
	
}
