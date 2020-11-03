package view;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginController {
	
	@FXML private Button login;
	@FXML private TextField username;

	public void start(Stage primaryStage) throws Exception {
		login.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (username.getText().equalsIgnoreCase("admin")) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/admin.fxml"));
					Parent root = null;
					try {
						root = (Pane) loader.load();
					} catch (IOException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					}
					AdminController adminController = loader.getController();
					Stage stage = new Stage();
					try {
						adminController.start(stage, primaryStage);
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					stage.setScene(new Scene(root, 600, 418));
					stage.setResizable(false);
					stage.show();
					primaryStage.hide();
				}
			}
		});
	}
}
	


