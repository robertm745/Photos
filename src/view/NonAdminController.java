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
	@FXML private ListView<Album> listView;
    @FXML private Button search;
    @FXML private Button createAlbum;
    @FXML private Button renameAlbum;
    @FXML private Button deleteAlbum;
    @FXML private Button openAlbum;
    @FXML private Button logoutButton;
    @FXML private Button cancel;
    @FXML private Button save;
    @FXML private TextField albumTextField;
    @FXML private Text errorText;
    @FXML private Text promptText;
    @FXML private Text numPics;
    @FXML private Text dateRange;
    
    private AlbumList albums;
    private int userIndex;
    private ObservableList<Album> obsList;
    protected UserList userList;
    private boolean rename;
    protected NonAdminController nac;
    
    
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
    			dateRange.setVisible(true);
    			numPics.setVisible(true);
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
    					userList.getList().get(userIndex).getAlbumList().addAlbum(a);
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
    }
    
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
