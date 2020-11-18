package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;

// TODO: Auto-generated Javadoc
/**
 * The Class Album. It contains Photos.
 */
/**
 * @author Robert
 * @author Mustafa
 *
 */
public class Album implements Serializable {

	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The name of Album. */
	private String name;
	
	/** The photos inside Album. */
	private ArrayList<Photo> photos;
	
	/** The paths to Photos. */
	private ArrayList<String> paths;
	
	/** The date range of Photos inside Album. */
	private String dateRange;
	
	/**
	 * Instantiates a new album.
	 *
	 * @param name the name of album
	 */
	public Album(String name) {
		this.name = name;
		this.photos = new ArrayList<Photo>();
		this.paths = new ArrayList<String>();
		this.dateRange = "";
	}
	
	/**
	 * Gets the photos.
	 *
	 * @return the photos
	 */
	public ArrayList<Photo> getPhotos() {
		return this.photos;
	}
	
	/**
	 * Gets the paths of photos.
	 *
	 * @return the paths of photos
	 */
	public ArrayList<String> getPaths() {
		return this.paths;
	}
	
	/**
	 * Adds the photo to the album.
	 *
	 * @param photo the photo to add
	 */
	public void addPhoto(Photo photo) {
		this.photos.add(photo);
		this.paths.add(photo.toString());
		this.setDateRange();
	}
	
	/**
	 * Delete photo from album.
	 *
	 * @param photo the photo to delete
	 */
	public void deletePhoto(Photo photo) {
		this.photos.remove(photo);
		this.paths.remove(photo.toString());
		this.setDateRange();
	}
	
	/*
	public Photo getPhoto(Photo photo) {
		for (Photo p : photos) {
			System.out.println(p + " is this and param is " + photo);
			if (p.equals(photo)) {
				System.out.println(true);
				return p;
			}
		}
		return null;
	}
	*/
	
	/**
	 * Rename album.
	 *
	 * @param name the name of album to rename to
	 */
	public void rename(String name) {
		this.name = name;
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
		if (o == null || !(o instanceof Album))
			return false;
		Album temp = (Album) o;
		return this.toString().equalsIgnoreCase(temp.toString());
	}

	
	/**
	 * Compare to.
	 *
	 * @param a the album to compare to
	 * @return comparison int
	 */
	public int compareTo(Album a) {
			return this.toString().compareTo(a.toString());
	}

	/**
	 * Gets the size.
	 *
	 * @return the size of album (number of photos)
	 */
	public int getSize() {
		return this.photos.size();
	}
	
	/**
	 * Sets the date range of Album.
	 */
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
	
	/**
	 * Gets the date range.
	 *
	 * @return the date range
	 */
	public String getDateRange() {
		return this.dateRange;
	}
	
}
