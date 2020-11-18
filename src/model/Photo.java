package model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Photo implements Serializable, Comparable<Photo> {
	
	private static final long serialVersionUID = 1L;
	private String caption;
	private String path;
	private Calendar datetime;
	private HashMap<String, ArrayList<String>> tags;
	
	
	public Photo(String path, long datetime) {
		this.path = path;
		this.datetime = Calendar.getInstance();
		this.datetime.setTimeInMillis(datetime);
		this.datetime.set(Calendar.MILLISECOND, 0);
		this.caption = "";
		this.tags = new HashMap<String, ArrayList<String>>();
	}
	
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
	
	public void setCaption (String value) {
		this.caption = value;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public Calendar getDateTime() {
		return this.datetime;
	}
	
	public void setDateTime() {
		File file = new File(this.path);
		this.datetime.setTimeInMillis(file.lastModified());
		this.datetime.set(Calendar.MILLISECOND, 0);
	}
	
	public HashMap<String, ArrayList<String>> getTags() {
		return this.tags;
	}
	
	public void addTag(String key, String value) {
		if (tags.containsKey(key)) {
			tags.get(key).add(value);
		} else {
			tags.put(key, new ArrayList<>());
			tags.get(key).add(value);
		}
	}
	
	public void removeTag(String value) {
		String[] values = value.split("\\|");
		tags.get(values[0]).remove(values[1]);
		if (tags.get(values[0]).size() == 0) {
			tags.remove(values[0]);
		}
		
		
	}
	
	public ArrayList<String> getTagsList(){
		ArrayList<String> retValue = new ArrayList<String>();
		
		for (String key: tags.keySet()) {
			for(String value: tags.get(key)) {
				retValue.add(key + "|" + value);
			}
		}
		
		return retValue;
	}
	
		
	public String toString() {
		return this.path;
	}
	
	public boolean eqauls(Object o) {
		if (o == null || !(o instanceof Photo))
			return false;
		Photo p = (Photo) o;
		return p.toString().equalsIgnoreCase(this.toString()) && this.getCaption().equalsIgnoreCase(p.getCaption());
	}
	
	@Override
	public int compareTo(Photo p) {
		if (this.getDateTime().getTime().equals(p.getDateTime().getTime())) 
			return 0;
		return this.getDateTime().getTime().before(p.getDateTime().getTime()) ? 1 : -1;
	}

}
