package controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.Parent;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import view.*;
import model.*;

public class Photos extends Application {
	
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
	
	public static void reset() {
		UserList list = new UserList();
		User u = new User("user");
		list.addUser(u);
		u.getAlbumList().addAlbum(new Album("album"));
		try {
			Photo p = new Photo(new Image(new FileInputStream("src/stock/stock1.jpeg")));
			p.setHashCode("src/stock/stock1.jpeg");
			u.getAlbumList().getAlbum(0).getPhotoList().addPhoto(p);
			p = new Photo(new Image(new FileInputStream("src/stock/stock2.jpeg")));
			p.setHashCode("src/stock/stock2.jpeg");
			u.getAlbumList().getAlbum(0).getPhotoList().addPhoto(p);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		UserList.writeList(list);
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		reset();
		
		launch(args);

		
		
	}
}
