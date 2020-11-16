package view;

import java.io.IOException;
import controller.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class NonAdminController {
	@FXML
	private ListView<Album> listView;
	@FXML
	private Text promptText;
	@FXML
	private Button search;
	@FXML
	private Button createAlbum;
	@FXML
	private Button renameAlbum;
	@FXML
	private Button deleteAlbum;
	@FXML
	private Button openAlbum;
	@FXML
	private Button logoutButton;
	@FXML
	private TextField albumTextField;
	@FXML
	private Text errorText;
	@FXML
	private Button cancel;
	@FXML
	private Button save;

//	private AlbumList albums;
	private String user;
	private int userIndex;
	private ObservableList<Album> obsList;
	private UserList userList;
	private boolean rename;

	public void start(Stage primaryStage) {
		
		promptText.setText("Welcome " + user + "!");
		
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
	}

	
	public void transferMessage(String message) {
        //Display the message
        user = message;
    }
}
