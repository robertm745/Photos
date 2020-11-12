package model;

import java.io.Serializable;

public class User implements Serializable {
	private String name;
	
	public User(String name) {
		// TODO Auto-generated constructor stub
		this.name = name;
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
