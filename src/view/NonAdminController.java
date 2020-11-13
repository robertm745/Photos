package view;

import java.io.IOException;
import java.util.ArrayList;

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
    @FXML private Text promptText;
    @FXML private Button search;
    @FXML private Button createAlbum;
    @FXML private Button renameAlbum;
    @FXML private Button deleteAlbum;
    @FXML private Button openAlbum;
    @FXML private Button logoutButton;
    @FXML private TextField albumTextField;
    @FXML private Text errorText;
    @FXML private Button cancel;
    @FXML private Button save;
    
    private AlbumList albums;
    private int userIndex;
    private ObservableList<Album> obsList;
    private UserList userList;
    private boolean rename;
    
    public void start(Stage newStage, User user) {
    	try {
			this.userList = UserList.readList();
		} catch (ClassNotFoundException | IOException e2) {
			e2.printStackTrace();
		}
		newStage.setTitle(user + "'s Albums");
    	promptText.setText("Welcome, " + user + "!");
    	userIndex = userList.getUserIndex(user);
    	albums = userList.getList().get(userList.getUserIndex(user)).getAlbumList();
    	obsList = FXCollections.observableArrayList(albums.getList());
    	obsList.sort((a,b) -> a.compareTo(b));
    	listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
		
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
    				update();
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
    		}
    	});
    	
    	cancel.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
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
    				Album a = new Album(albumTextField.getText());
    				if (!albums.getList().contains(a)) {
    					userList.getList().get(userIndex).getAlbumList().getAlbum(index).rename(albumTextField.getText());
    					update();
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
    					update();
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
						albumCon.start(stage,  newStage, a, user);
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
    
    public void update() {
		try {
			UserList.writeList(userList);
			userList = UserList.readList();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		albums = userList.getList().get(userIndex).getAlbumList();
		obsList = FXCollections.observableArrayList(albums.getList());
		obsList.sort((a,b) -> a.compareTo(b));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
    }
    

}
