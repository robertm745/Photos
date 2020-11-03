package view;

import java.io.IOException;

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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AdminController {

    @FXML private ListView<?> listView;
    @FXML private Button newUserButton;
    @FXML private Button deleteUserButton;
    @FXML private Button logoutButton;
    @FXML private TextField newUserTextField;
    @FXML private Text statusText;

    public void start(Stage newStage, Stage oldStage) throws Exception {
    	logoutButton.setOnAction(new EventHandler<ActionEvent>() {
    		public void handle(ActionEvent e) {
    			newStage.close();
    			oldStage.show();
    		}
    	});
    }
    
    
}
