package model;

import java.util.ArrayList;
import java.io.*;

public class UserList implements Serializable {

	private static final long serialVersionUID = 1L;
	private ArrayList<User> users;
	
	public static final String storeDir = "src/controller";
	public static final String storeFile = "list.dat";
	
	public UserList() {
		this.users = new ArrayList<User>();		
	}
	
	public void addUser(User user) {
		this.users.add(user);
	}
	
	public void delete(User user) throws IOException {
		this.users.remove(user);
		writeList(this);
	}
	
	public ArrayList<User> getList() {
		return this.users;
	}
	
	public int getUserIndex(User u) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(u))
				return i;
		}
		return -1;
	}
	
	public static void writeList(UserList ul) throws IOException {
		//ul.printUsers();
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
		oos.writeObject(ul);
		oos.close();
	}
	
	public static UserList readList() throws ClassNotFoundException, IOException {
		ObjectInputStream ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
		UserList list = (UserList) ois.readObject();
		ois.close();
		return list;
	}
	
	public void printUsers() {
		System.out.println("Users: \n");
		for (int i = 0; i < this.users.size(); i++) {
			System.out.println(users.get(i) + " Albums:");
			users.get(i).printAlbums();
			System.out.println("\n");
		}
	}

	public boolean contains(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(user))
				return true;
		}
		return false;
	}

}
