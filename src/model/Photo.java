package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

// TODO: Auto-generated Javadoc
/**
 * The Class Photo.
 * 
 * @author Mustafa
 * @author Robert
 */
public class Photo implements Serializable, Comparable<Photo> {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The caption. */
	private String caption;
	
	/** The path to Photo file. */
	private String path;
	
	/** The datetime Photo modified. */
	private Calendar datetime;
	
	/** The tags. */
	private HashMap<String, ArrayList<String>> tags;
	
	
	/**
	 * Instantiates a new photo.
	 *
	 * @param path the path to Photo
	 * @param datetime the datetime photo modified
	 */
	public Photo(String path, long datetime) {
		this.path = path;
		this.datetime = Calendar.getInstance();
		this.datetime.setTimeInMillis(datetime);
		this.datetime.set(Calendar.MILLISECOND, 0);
		this.caption = "";
		this.tags = new HashMap<String, ArrayList<String>>();
	}
	
	/**
	 * Instantiates a new photo.
	 *
	 * @param path the path to Photo
	 * @param datetime the datetime photo modified
	 * @param caption the caption
	 * @param tags the tags
	 */
	public Photo(String path, long datetime, String caption, HashMap<String, ArrayList<String>> tags ) {
		this.path = path;
		this.datetime = Calendar.getInstance();
		this.datetime.setTimeInMillis(datetime);
		this.datetime.set(Calendar.MILLISECOND, 0);
		this.caption = caption;
		
		this.tags = new HashMap<String, ArrayList<String>>();
		
		for (String key : tags.keySet()) {
			this.tags.put(key, new ArrayList<String>());
			for (String val : tags.get(key)) {
				this.tags.get(key).add(val);
			}
		}
	}
	
	/**
	 * Sets the caption.
	 *
	 * @param value the new caption
	 */
	public void setCaption (String value) {
		this.caption = value;
	}
	
	/**
	 * Gets the caption.
	 *
	 * @return the caption
	 */
	public String getCaption() {
		return this.caption;
	}
	
	/**
	 * Gets the date time.
	 *
	 * @return the date time
	 */
	public Calendar getDateTime() {
		return this.datetime;
	}
	
	/**
	 * Sets the date time.
	 */
	public void setDateTime() {
		File file = new File(this.path);
		this.datetime.setTimeInMillis(file.lastModified());
		this.datetime.set(Calendar.MILLISECOND, 0);
	}
	
	/**
	 * Gets the tags.
	 *
	 * @return the tags
	 */
	public HashMap<String, ArrayList<String>> getTags() {
		return this.tags;
	}
	
	/**
	 * Adds the tag.
	 *
	 * @param key the key
	 * @param value the value
	 */
	public void addTag(String key, String value) {
		if (tags.containsKey(key)) {
			tags.get(key).add(value);
		} else {
			tags.put(key, new ArrayList<>());
			tags.get(key).add(value);
		}
	}
	
	/**
	 * Removes the tag.
	 *
	 * @param value the value
	 */
	public void removeTag(String value) {
		String[] values = value.split("\\|");
		tags.get(values[0]).remove(values[1]);
		if (tags.get(values[0]).size() == 0) {
			tags.remove(values[0]);
		}
		
		
	}
	
	/**
	 * Gets the tags list.
	 *
	 * @return the tags list
	 */
	public ArrayList<String> getTagsList(){
		ArrayList<String> retValue = new ArrayList<String>();
		
		for (String key: tags.keySet()) {
			for(String value: tags.get(key)) {
				retValue.add(key + "|" + value);
			}
		}
		
		return retValue;
	}
	
		
	/**
	 * To string.
	 *
	 * @return the string
	 */
	public String toString() {
		return this.path;
	}
	
	/**
	 * Eqauls.
	 *
	 * @param o the object to check equality
	 * @return true, if successful
	 */
	public boolean eqauls(Object o) {
		if (o == null || !(o instanceof Photo))
			return false;
		Photo p = (Photo) o;
		return p.toString().equalsIgnoreCase(this.toString()) && this.getCaption().equalsIgnoreCase(p.getCaption());
	}
	
	/**
	 * Compare to.
	 *
	 * @param p the photo to compare to
	 * @return the int
	 */
	@Override
	public int compareTo(Photo p) {
		if (this.getDateTime().getTime().equals(p.getDateTime().getTime())) 
			return 0;
		return this.getDateTime().getTime().before(p.getDateTime().getTime()) ? 1 : -1;
	}

}
