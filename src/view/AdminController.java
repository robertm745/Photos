package view;

import java.io.IOException;

import controller.Photos;
import model.*;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminController.
 *
 * @author Robert
 * @author Mustafa
 */
public class AdminController {

    /** The list view. */
    @FXML private ListView<User> listView;
    
    /** The new user button. */
    @FXML private Button newUserButton;
    
    /** The delete user button. */
    @FXML private Button deleteUserButton;
    
    /** The logout button. */
    @FXML private Button logoutButton;
    
    /** The new user text field. */
    @FXML private TextField newUserTextField;
    
    /** The status text. */
    @FXML private Text statusText;
    
    /** The obs list. */
    private ObservableList<User> obsList;
    
    /** The list. */
    private UserList list;

    /**
     * Starts the controller
     *
     * @param newStage the new stage
     * @throws Exception the exception
     */
    public void start(Stage newStage) throws Exception {
		newStage.setTitle("Admin");
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
    			try {
					new Photos().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
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
    						saveData();
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
    
    /**
     * Saves the data.
     */
    public void saveData() {
		UserList.writeList(list);
		list = UserList.readList();
		obsList = FXCollections.observableArrayList(list.getList());
		obsList.sort((a,b) -> a.compareTo(b));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
		statusText.setVisible(false);
		newUserTextField.clear();
    }
    
    
}
