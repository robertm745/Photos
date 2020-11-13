package controller;


import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
		list.addUser(new User("admin"));
		//list.addUser(new User("user3"));
		//list.addUser(new User("user2"));
		//list.printUsers();
		UserList listB;
		try {
			UserList.writeList(list);
			listB = UserList.readList();
			listB.printUsers();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//reset();
		
		launch(args);

		
		
	}
}
