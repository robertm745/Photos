package model;

import java.io.File;
import java.io.Serializable;
import java.util.Calendar;

public class Photo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String caption;
	private String path;
	private Calendar datetime;
	
	
	public Photo(String path, long datetime) {
		this.path = path;
		this.datetime = Calendar.getInstance();
		this.datetime.setTimeInMillis(datetime);
		this.datetime.set(Calendar.MILLISECOND, 0);
		this.caption = "";
	}
	
	public void setCaption (String value) {
		this.caption = value;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public String getDateTime() {
		return this.datetime.getTime().toString();
	}
	
	public void setDateTime() {
		File file = new File(this.path);
		this.datetime.setTimeInMillis(file.lastModified());
		this.datetime.set(Calendar.MILLISECOND, 0);
		//System.out.println(this.datetime.getTime().toString() + " for file " + this.path);
	}
	
	public String toString() {
		return this.path;
	}
	
	public boolean eqauls(Object o) {
		if (o == null || !(o instanceof Photo))
			return false;
		Photo p = (Photo) o;
		return p.toString().equalsIgnoreCase(this.toString());
	}

}
