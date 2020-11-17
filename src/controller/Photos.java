package controller;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import model.User;
import model.UserList;
import view.LoginController;

public class Photos extends Application {

	public Photos() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		
		UserList userlist = UserList.readList();
		
		// TODO Auto-generated method stub
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("/view/Login.fxml"));
		Pane root = (Pane) loader.load();

		LoginController loginController = loader.getController();
		loginController.transferMessage(userlist);
		loginController.start(primaryStage);

		Scene scene = new Scene(root, 990, 770);
		primaryStage.setScene(scene);
		primaryStage.setTitle("Photos login");
		primaryStage.setResizable(false);
		primaryStage.show();

	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);

	}

}
