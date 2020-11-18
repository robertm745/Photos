package model;

import java.util.ArrayList;
import java.io.*;

// TODO: Auto-generated Javadoc
/**
 * The Class UserList.
 * 
 * @author Mustafa
 * @author Robert
 */
public class UserList implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;
	
	/** The users. */
	private ArrayList<User> users;
	
	/** The Constant storeDir. */
	public static final String storeDir = "data";
	
	/** The Constant storeFile. */
	public static final String storeFile = "data.dat";
	
	/**
	 * Instantiates a new user list.
	 */
	public UserList() {
		this.users = new ArrayList<User>();		
	}
	
	/**
	 * Adds the user.
	 *
	 * @param user the user
	 */
	public void addUser(User user) {
		this.users.add(user);
	}
	
	/**
	 * Delete.
	 *
	 * @param user the user
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public void delete(User user) throws IOException {
		this.users.remove(user);
		writeList(this);
	}
	
	/**
	 * Gets the list of users.
	 *
	 * @return the list
	 */
	public ArrayList<User> getList() {
		return this.users;
	}
	
	/**
	 * Gets the user index.
	 *
	 * @param u the user to get index of
	 * @return the user index
	 */
	public int getUserIndex(User u) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(u))
				return i;
		}
		return -1;
	}
	
	/**
	 * Write list.
	 *
	 * @param ul the UserList to write
	 */
	public static void writeList(UserList ul) {
		//ul.printUsers();
		ObjectOutputStream oos;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(storeDir + File.separator + storeFile));
			oos.writeObject(ul);
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * Read list.
	 *
	 * @return the UserList
	 */
	public static UserList readList() {
		ObjectInputStream ois;
		UserList list = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(storeDir + File.separator + storeFile));
			list = (UserList) ois.readObject();
			ois.close();
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return list;
	}
	
	/**
	 * Prints the users.
	 */
	public void printUsers() {
		System.out.println("Users: \n");
		for (int i = 0; i < this.users.size(); i++) {
			System.out.println(users.get(i) + " Albums:");
			users.get(i).printAlbums();
			System.out.println("\n");
		}
	}

	/**
	 * Checks if contains User.
	 *
	 * @param user the user
	 * @return true, if successful
	 */
	public boolean contains(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).equals(user))
				return true;
		}
		if (user.toString().equalsIgnoreCase("admin"))
			return true;
		return false;
	}

}
