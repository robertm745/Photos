package model;

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<Album> albums;
	
	public AlbumList() {
		// TODO Auto-generated constructor stub
		this.albums = new ArrayList<Album>();
	}
	public void addAlbum(Album a) {
		this.albums.add(a);
	}
	public void deleteAlbum(Album a) {
		this.albums.remove(a);
	}
	public ArrayList<Album> getList() {
		return this.albums;
	}

}
