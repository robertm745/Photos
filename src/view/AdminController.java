package view;

import java.io.IOException;

import controller.Photos;
import model.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AdminController {

	@FXML
	private ListView<User> listView;
	@FXML
	private Button newUserButton;
	@FXML
	private Button deleteUserButton;
	@FXML
	private Button logoutButton;
	@FXML
	private TextField newUserTextField;
	@FXML
	private Text statusText;

	private ObservableList<User> obsList;
	private UserList list;

	public void start(Stage primaryStage) {
    	try {
			list = UserList.readList();
		} catch (ClassNotFoundException | IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
    	obsList = FXCollections.observableArrayList(list.getUsers());
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
		
		
		
		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			try {
    	            //Load second scene
    	            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
    	            Parent root = loader.load();
    	             
    	            //Get controller of scene2
    	            LoginController loginController = loader.getController();  
    	            loginController.start(primaryStage);
    	            primaryStage.setScene(new Scene(root, 800, 600));
    	            primaryStage.setTitle("Photos login");
    	            primaryStage.show();
    	        } catch (IOException ex) {
    	            System.err.println(ex);
    	        }
				}
    		
    	});
		
		deleteUserButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!listView.getSelectionModel().isEmpty()) {
    				User u = listView.getSelectionModel().getSelectedItem();
					int index = listView.getSelectionModel().getSelectedIndex();
					
						list.removeUser(u);
						saveData();
						if (list.getList().size() == index)
							index--;
						listView.getSelectionModel().select(index);
    			}
    		}
    	});
		
		
		newUserButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!newUserTextField.getText().isBlank()) {
    				User u = new User(newUserTextField.getText());
    				if (!list.contains(u)) {
    					list.addUser(u);
    					saveData();
    					listView.getSelectionModel().select(u);
    					listView.requestFocus();
    				} else {
        				statusText.setVisible(true);
        				statusText.setText("Error: Duplicate User");
    				}
    			} else {
    				statusText.setVisible(true);
    				statusText.setText("Error: User Name Required");
    			}
    		}
    	});
			
		
	}
	
	public void saveData() {
		try {
			UserList.write(list);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			list = UserList.readList();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		obsList = FXCollections.observableArrayList(list.getList());
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
		statusText.setVisible(false);
		newUserTextField.clear();
    }

}
