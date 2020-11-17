package model;

import java.io.Serializable;
import java.util.ArrayList;

public class User implements Serializable {
	private static final long serialVersionUID = 1L;
	private String username;
	private ArrayList<Album> albums;

	private ArrayList<String> tags;
	

	public User(String username) {
		// TODO Auto-generated constructor stub
		this.username = username;
		albums = new ArrayList<Album>();
	}
	
	public void addAlbum(Album album) {
		albums.add(album);
	}
	
	public void deleteAlbum(Album album) {
		this.albums.remove(album);
	}
	
	public void addTag(String tag) {
		tags.add(tag);
	}

	public ArrayList<Album> getAlbums() {
		return albums;
	}

	public boolean contains(Album album) {
		if (this.albums.contains(album)) return true;
		else return false;
	}
	
	public String getUsername() {
		return username;
	}
	
	public String toString() {
		return username;
	}

	

}
