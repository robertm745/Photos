package model;

import java.io.Serializable;
import java.util.ArrayList;

// TODO: Auto-generated Javadoc
/**
 * The Class User.
 * 
 * @author Mustafa
 * @author Robert
 */
public class User implements Serializable {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The name. */
	private String name;
	
	/** The albums. */
	private AlbumList albums;
	
	/** The tags. */
	private ArrayList<String> tags;
	
	/**
	 * Instantiates a new user.
	 *
	 * @param name the name of user
	 */
	public User(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
		this.albums = new AlbumList();
		tags = new ArrayList<String>();
		tags.add("location");
		tags.add("person");
	}
	
	/**
	 * Adds the tag.
	 *
	 * @param e the tag type
	 */
	public void addTag(String e) {
		tags.add(e);
	}
	
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public ArrayList<String> getTags(){
		return tags;
	}
	
	/**
	 * Gets the album list.
	 *
	 * @return the album list
	 */
	public AlbumList getAlbumList() {
		return this.albums;
	}
	
	/**
	 * Sets the album list.
	 *
	 * @param al the new album list
	 */
	public void setAlbumList(AlbumList al) {
		this.albums = al;
	}
	
	/**
	 * Prints the albums.
	 */
	public void printAlbums() {
		for (Album a: albums.getList()) {
			System.out.println(a);
		}
	}
	
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return this.name;
	}
	
	/**
	 * Equals.
	 *
	 * @param o the object to check equality with 
	 * @return true, if successful
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof User))
			return false;
		User temp = (User) o;
		return this.toString().equalsIgnoreCase(temp.toString());
	}

	
	/**
	 * Compare to.
	 *
	 * @param u the user to compare to
	 * @return the int
	 */
	public int compareTo(User u) {
			return this.toString().compareTo(u.toString());
	}
	
}
