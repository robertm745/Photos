package model;

import java.util.ArrayList;
import java.io.*;

public class UserList implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList<User> users;
	public static final String storeDir = "src/controller";
	public static final String storeFile = "list.dat";
	public UserList() {
		// TODO Auto-generated constructor stub
		this.users = new ArrayList<User>();
		
	}
	public void addUser(User user) {
		this.users.add(user);
	}
	public void delete(User user) throws IOException {
		this.users.remove(user);
		this.writeList();
	}
	public ArrayList<User> getList() {
		return this.users;
	}
	public void writeList() throws IOException {
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(this);
		oos.close();
	}
	public static UserList readList() throws ClassNotFoundException, IOException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		UserList list = (UserList) ois.readObject();
		ois.close();
		return list;
	}
	public void printUsers() {
		System.out.println("Users: ");
		for (int i = 0; i < this.users.size(); i++) {
			System.out.println(users.get(i));
		}
	}

	public boolean contains(User user) {
		if (user.equals("admin"))
			return true;
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(user))
				return true;
		}
		return false;
	}

}
