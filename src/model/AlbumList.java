package model;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class AlbumList containg list of Albums
 * 
 * @author Mustafa
 * @author Robert
 */
public class AlbumList implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The albums. */
	private ArrayList<Album> albums;
	
	/**
	 * Instantiates a new album list.
	 */
	public AlbumList() {
		this.albums = new ArrayList<Album>();
	}
	
	/**
	 * Adds the album.
	 *
	 * @param a the a
	 */
	public void addAlbum(Album a) {
		this.albums.add(a);
	}
	
	/**
	 * Delete album.
	 *
	 * @param a the album to delete
	 */
	public void deleteAlbum(Album a) {
		this.albums.remove(a);
	}
	
	/**
	 * Gets the list.
	 *
	 * @return the list
	 */
	public ArrayList<Album> getList() {
		return this.albums;
	}

	/**
	 * Gets the album index.
	 *
	 * @param a the album
	 * @return the album index
	 */
	public int getAlbumIndex(Album a) {
		for (int i = 0; i < albums.size(); i++) {
			if (albums.get(i).equals(a)) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * Gets the album.
	 *
	 * @param index the index
	 * @return the album
	 */
	public Album getAlbum(int index) {
		return albums.get(index);
	}
		
}
