package model;

import java.io.Serializable;

public class User implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private AlbumList albums;
	
	public User(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.albums = new AlbumList();        	
		albums.addAlbum(new Album("sample2"));
    	albums.addAlbum(new Album("sample1"));
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

	
	//@Override
	public int compareTo(User u) {
			return this.toString().compareTo(u.toString());
	}
	
}
