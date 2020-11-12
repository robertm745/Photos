package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;


public class NonAdminController {
	@FXML private ListView<?> albumsList;
    @FXML private Text promptText;
    @FXML private Button search;
    @FXML private Button createAlbum;
    @FXML private Button renameAlbum;
    @FXML private Button deleteAlbum;
    @FXML private Button openAlbum;
    @FXML private Button logoutButton;
    
    public void start(Stage newStage, Stage oldStage, User user) {
    	promptText.setText("Welcome, " + user + "!");
    	
    	logoutButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			newStage.close();
    			oldStage.show();
    		}
    	});
    }

}
