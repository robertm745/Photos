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


// TODO: Auto-generated Javadoc
/**
 * The Class NonAdminController.
 * 
 * @author Mustafa
 * @author Robert
 */
public class NonAdminController {
	
	/** The list view. */
	@FXML private ListView<Album> listView;
    
    /** The search. */
    @FXML private Button search;
    
    /** The create album. */
    @FXML private Button createAlbum;
    
    /** The rename album. */
    @FXML private Button renameAlbum;
    
    /** The delete album. */
    @FXML private Button deleteAlbum;
    
    /** The open album. */
    @FXML private Button openAlbum;
    
    /** The logout button. */
    @FXML private Button logoutButton;
    
    /** The cancel. */
    @FXML private Button cancel;
    
    /** The save. */
    @FXML private Button save;
    
    /** The album text field. */
    @FXML private TextField albumTextField;
    
    /** The error text. */
    @FXML private Text errorText;
    
    /** The prompt text. */
    @FXML private Text promptText;
    
    /** The num pics. */
    @FXML private Text numPics;
    
    /** The date range. */
    @FXML private Text dateRange;
    
    /** The albums. */
    private AlbumList albums;
    
    /** The user index. */
    private int userIndex;
    
    /** The obs list. */
    private ObservableList<Album> obsList;
    
    /** The user list. */
    protected UserList userList;
    
    /** The rename. */
    private boolean rename;
    
    /** The nac. */
    protected NonAdminController nac;
    
    
    /**
     * Starts the controller.
     *
     * @param newStage the new stage
     * @param user the user
     */
    public void start(Stage newStage, User user) {
    	this.nac = this;
		newStage.setTitle(user + "'s Albums");
    	promptText.setText("Welcome, " + user + "!");
    	
		this.userList = UserList.readList();
    	userIndex = userList.getUserIndex(user);
    	albums = userList.getList().get(userList.getUserIndex(user)).getAlbumList();
		for (Album a : userList.getList().get(userIndex).getAlbumList().getList()) {
			for (Photo p : a.getPhotos()) {
				p.setDateTime();
			}
		}
		
		saveData();
		
    	obsList = FXCollections.observableArrayList(albums.getList());
    	obsList.sort((a,b) -> a.compareTo(b));
    	listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		updateListView();
		
		listView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> updateListView());
		
		
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
    	
    	deleteAlbum.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!listView.getSelectionModel().isEmpty()) {
    				int sel = listView.getSelectionModel().getSelectedIndex();
    				userList.getList().get(userIndex).getAlbumList().deleteAlbum(listView.getSelectionModel().getSelectedItem());
    				saveData();
    				if (sel == albums.getList().size()) 
    					sel--;
    				listView.getSelectionModel().select(sel);
    			}
    		}
    	});
    	
    	createAlbum.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			listView.setDisable(true);
    			albumTextField.setVisible(true);
    			cancel.setVisible(true);
    			save.setVisible(true);
    			deleteAlbum.setVisible(false);
    			renameAlbum.setVisible(false);
    			createAlbum.setVisible(false);
    			openAlbum.setVisible(false);
    			search.setVisible(false);
    			dateRange.setVisible(false);
    			numPics.setVisible(false);
    		}
    	});
    	
    	cancel.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			updateListView();
    			albumTextField.setVisible(false); 
    			albumTextField.clear();
    			cancel.setVisible(false);
    			save.setVisible(false); 
    			errorText.setVisible(false);
    			deleteAlbum.setVisible(true);
    			renameAlbum.setVisible(true);
    			createAlbum.setVisible(true);
    			openAlbum.setVisible(true);
    			search.setVisible(true);
    			listView.setDisable(false);
    			listView.requestFocus();
    		}
    	});
    	
    	save.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (rename) {
    				int index = userList.getList().get(userIndex).getAlbumList().getAlbumIndex(listView.getSelectionModel().getSelectedItem());
    				if (albumTextField.getText().equals(listView.getSelectionModel().getSelectedItem().toString())) {
    					cancel.fire();
    					return;
    				}
    				Album a = new Album(albumTextField.getText());
    				if (!albums.getList().contains(a)) {
    					userList.getList().get(userIndex).getAlbumList().getAlbum(index).rename(albumTextField.getText());
    					saveData();
        				rename = false;
        				cancel.fire();
    					listView.getSelectionModel().select(a);
    				}
    				else {
    					errorText.setVisible(true);
    					errorText.setText("Error: Duplicate album");
    				}
    			}
    			else if (!albumTextField.getText().isBlank()) {
    				Album a = new Album(albumTextField.getText());
    				if (!albums.getList().contains(a)) {
    					albums.addAlbum(a);
    					saveData();
    					listView.getSelectionModel().select(a);
    					cancel.fire();
    				} else {
    					errorText.setVisible(true);
    					errorText.setText("Error: Duplicate album");
    				}
    			} else {
					errorText.setVisible(true);
					errorText.setText("Error: Album name required");
    			}
    		}
    	});
    	
    	renameAlbum.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (listView.getSelectionModel().isEmpty())
    				return;
    			String name = listView.getSelectionModel().getSelectedItem().toString();
    			createAlbum.fire();
    			albumTextField.setText(name);
    			rename = true;
    		}
    	});
    	
    	openAlbum.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!obsList.isEmpty()) {
    				Album a = listView.getSelectionModel().getSelectedItem();
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/albumview.fxml"));
					Parent root = null;
					try {
						root = (Pane) loader.load();
						AlbumController albumCon = loader.getController();
						Stage stage = new Stage();
						albumCon.start(stage,  newStage, a, user, nac);
						stage.setScene(new Scene(root, 987, 770));
						stage.setResizable(false);
						stage.show();
						newStage.hide();
					} catch (IOException e1) {
						e1.printStackTrace();
					}
    			}
    		}
    	});
    	
    	
    	search.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			FXMLLoader loader = new FXMLLoader();
    			loader.setLocation(getClass().getResource("/view/search.fxml"));
    			Parent root = null;
    			try {
					root = (Pane) loader.load();
					SearchController sc = loader.getController();
					Stage stage = new Stage();
					sc.start(stage, newStage, user, nac);
					stage.setScene(new Scene(root, 600, 400));
					stage.setResizable(false);
					stage.show();
					newStage.hide();
					
				} catch (IOException e1) {
					e1.printStackTrace();
				}
    		}
    	});
    	
    	
    	
    }
    
    /**
     * Saves the data.
     */
    public void saveData() {
		UserList.writeList(userList);
		userList = UserList.readList();
		albums = userList.getList().get(userIndex).getAlbumList();
		obsList = FXCollections.observableArrayList(albums.getList());
		obsList.sort((a,b) -> a.compareTo(b));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
    }
    
    
    /**
     * Update list view.
     */
    public void updateListView() {
    	Album a = listView.getSelectionModel().getSelectedItem();
    	if (a != null ) {
    		numPics.setVisible(true);
    		numPics.setText(a.getSize() + "");
    		dateRange.setVisible(true);
    		dateRange.setText(a.getDateRange());
    	}
    	else {
    		numPics.setVisible(false);	
    		dateRange.setVisible(false);
    	}
    	
    	
    }

}
