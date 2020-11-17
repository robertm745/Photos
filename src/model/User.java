package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private AlbumList albums;
	private ArrayList<String> tags;
	
	public User(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.albums = new AlbumList();
		tags = new ArrayList<String>();
		tags.add("location");
		tags.add("person");
	}
	
	public void addTag(String e) {
		tags.add(e);
	}
	
	public ArrayList<String> getTags(){
		return tags;
	}
	
	public AlbumList getAlbumList() {
		return this.albums;
	}
	
	public void setAlbumList(AlbumList al) {
		this.albums = al;
	}
	
	public void printAlbums() {
		for (Album a: albums.getList()) {
			System.out.println(a);
		}
	}
	
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof User))
			return false;
		User temp = (User) o;
		return this.toString().equalsIgnoreCase(temp.toString());
	}

	
	public int compareTo(User u) {
			return this.toString().compareTo(u.toString());
	}
	
}
