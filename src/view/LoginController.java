package view;

import java.io.IOException;
import java.util.*;
import model.*;
import view.*;
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
import model.UserList;

public class LoginController {
	
	@FXML private Button login;
	@FXML private TextField username;
	@FXML private Text statusText;
	

	public void start(Stage primaryStage) throws Exception {
		primaryStage.setTitle("Photos Library");
		username.clear();
		login.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				if (username.getText().equalsIgnoreCase("admin")) {
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/admin.fxml"));
					Parent root = null;
					try {
						root = (Pane) loader.load();
						AdminController adminController = loader.getController();
						Stage stage = new Stage();
						adminController.start(stage);					
						stage.setScene(new Scene(root, 600, 418));
						stage.setResizable(false);
						stage.show();
						primaryStage.hide();
						statusText.setVisible(false);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				} else {
					try {
						User user = new User(username.getText());
						if (username.getText().isBlank()) {
							statusText.setText("Please enter username");
							statusText.setVisible(true);
						} else if (!UserList.readList().contains(user)) {
							statusText.setText("Error: User not found");
							statusText.setVisible(true);
						} else {						
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(getClass().getResource("/view/non_admin.fxml"));
							Parent root = null;
							root = (Pane) loader.load();
							NonAdminController nonAdmin = loader.getController();
							Stage stage = new Stage();
							nonAdmin.start(stage, user);
							stage.setScene(new Scene(root, 634, 475));
							stage.setResizable(false);
							stage.show();
							primaryStage.close();
							statusText.setVisible(false);
						}
						
					} catch (ClassNotFoundException | IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
}
	


