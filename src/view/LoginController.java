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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.User;
import model.UserList;

public class LoginController {
	@FXML
	private Button login;
	@FXML
	private TextField username;
	@FXML
	private Text statusText;
	
	UserList userlist;

	public void start(Stage primaryStage) {
		// TODO Auto-generated method stub
		login.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				if (username.getText().equalsIgnoreCase("admin")) {
					try {
						// Load second scene
						FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/admin.fxml"));
						Parent root = loader.load();

						// Get controller of scene2
						AdminController adminController = loader.getController();
						adminController.transferMessage(userlist);
						adminController.start(primaryStage);
						primaryStage.setScene(new Scene(root, 990, 770));
						primaryStage.setTitle("Admin system");
						primaryStage.show();
					} catch (IOException ex) {
						ex.printStackTrace();
					}

				}

				else {
					try {
						User user = userlist.getUser(username.getText());
						if (username.getText().isBlank()) {
							statusText.setFill(Color.RED);
							statusText.setText("Please enter username");
						} else if (!UserList.readList().contains(user)) {
							statusText.setFill(Color.RED);
							statusText.setText("Error: User not found");
						} else {
							FXMLLoader loader = new FXMLLoader();
							loader.setLocation(getClass().getResource("/view/non_admin.fxml"));
							Parent root;

							root = loader.load();
							// Get controller of scene2
							NonAdminController nonAdminController = loader.getController();
							nonAdminController.transferMessage(userlist, user);
							nonAdminController.start(primaryStage);
							// Show scene 2 in new window
							primaryStage.setScene(new Scene(root, 990, 770));
							primaryStage.setTitle("Non admin");
							primaryStage.show();

						}

					} catch (IOException e1) {
						e1.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}

		});

	}
	
	public void transferMessage(UserList userlist) {
		// Display the message
		this.userlist = userlist;
	}

}
