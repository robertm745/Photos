package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class UserList implements Serializable {
	private static final long serialVersionUID = 1L;

	public static final String storeDir = "src/model";
	public static final String storeFile = "data.dat";

	private ArrayList<User> users;

	public ArrayList<User> getUsers() {
		return users;
	}

	public UserList() {
		// TODO Auto-generated constructor stub
		this.users = new ArrayList<User>();
	}

	public void addUser(User user) {
		users.add(user);
	}

	public void removeUser(User user) {
		users.remove(user);
	}

	public static UserList readList() throws IOException, ClassNotFoundException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		UserList userlist = (UserList) ois.readObject();
		ois.close();
		return userlist;
	}

	public static void write(UserList userlist) throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(userlist);
		oos.close();
	}
	
	public User getUser(String username) {
		for (User user : users) {
			if (user.getUsername().equals(username))
				return user;
		}
		
		return new User("null");
	}

	public boolean contains(User user) {
		for (User value : users) {
			if (value.getUsername().equals(user.getUsername()))
				return true;
		}
		return false;
	}

}
