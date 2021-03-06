package controller;

import java.util.Calendar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import view.*;
import model.*;

// TODO: Auto-generated Javadoc
/**
 * The Class Photos containing main.
 */
/**
 * @author Robert
 * @author Mustafa
 *
 */
public class Photos extends Application {
	
	/**
	 * Start.
	 *
	 * @param primaryStage the primary stage
	 * @throws Exception the exception
	 */
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		Parent root = (Pane) loader.load();
		LoginController loginController = loader.getController();
		loginController.start(primaryStage);
		Scene scene = new Scene(root, 600, 400);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();
	}
	
	/**
	 * Reset. resets application which stock photos
	 */
	public static void reset() {
		UserList list = new UserList();
		User u = new User("stock");
		list.addUser(u);
		u.getAlbumList().addAlbum(new Album("stock"));
		Photo p = new Photo("data/stock1.jpeg", Calendar.getInstance().getTimeInMillis());
		u.getAlbumList().getAlbum(0).addPhoto(p);
		p = new Photo("data/stock2.jpeg", Calendar.getInstance().getTimeInMillis());
		u.getAlbumList().getAlbum(0).addPhoto(p);
		p = new Photo("data/stock3.jpeg", Calendar.getInstance().getTimeInMillis());
		u.getAlbumList().getAlbum(0).addPhoto(p);
		p = new Photo("data/stock4.jpeg", Calendar.getInstance().getTimeInMillis());
		u.getAlbumList().getAlbum(0).addPhoto(p);
		p = new Photo("data/stock5.jpeg", Calendar.getInstance().getTimeInMillis());
		u.getAlbumList().getAlbum(0).addPhoto(p);
		UserList.writeList(list);
	}

	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//reset();
		
		launch(args);

		
		
	}
}
