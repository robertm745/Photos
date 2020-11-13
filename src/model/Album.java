package model;

import java.io.Serializable;

public class Album implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	public Album(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
	}
	
	public String toString() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object o) {
		if (o == null || !(o instanceof Album))
			return false;
		Album temp = (Album) o;
		return this.toString().equalsIgnoreCase(temp.toString());
	}

	
	//@Override
	public int compareTo(Album a) {
			return this.toString().compareTo(a.toString());
	}
	
}
