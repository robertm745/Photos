package view;

import java.io.IOException;
import model.*;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class AdminController {

    @FXML private ListView<User> listView;
    @FXML private Button newUserButton;
    @FXML private Button deleteUserButton;
    @FXML private Button logoutButton;
    @FXML private TextField newUserTextField;
    @FXML private Text statusText;
    
    private ObservableList<User> obsList;
    private UserList list;

    public void start(Stage newStage, Stage oldStage) throws Exception {
    	list = UserList.readList();
    	obsList = FXCollections.observableArrayList(list.getList());
		obsList.sort((a,b) -> a.compareTo(b));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
		statusText.setVisible(false);
    	logoutButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			newStage.close();
    			oldStage.show();
    		}
    	});
    	deleteUserButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!listView.getSelectionModel().isEmpty()) {
    				try {
    					User u = listView.getSelectionModel().getSelectedItem();
    					int index = listView.getSelectionModel().getSelectedIndex();
    					if (u.toString().equals("admin")) {
            				statusText.setVisible(true);
            				statusText.setText("Error: Cannot delete admin");
            				newUserTextField.clear();
    					} else {
    						list.delete(u);
    						update();
    						if (list.getList().size() == index)
    							index--;
    						listView.getSelectionModel().select(index);
    					}
					} catch (IOException e1) {
						e1.printStackTrace();
					}
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
    					update();
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
    
    public void update() {
		try {
			UserList.writeList(list);
			list = UserList.readList();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		obsList = FXCollections.observableArrayList(list.getList());
		obsList.sort((a,b) -> a.compareTo(b));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
		statusText.setVisible(false);
		newUserTextField.clear();
    }
    
    
}
