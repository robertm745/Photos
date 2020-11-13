package view;

import java.io.IOException;
import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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
    private UserList uList;
    private boolean rename;
    
    public void start(Stage newStage, Stage oldStage, User user) {
    	try {
			this.uList = UserList.readList();
		} catch (ClassNotFoundException | IOException e2) {
			e2.printStackTrace();
		}
    	promptText.setText("Welcome, " + user + "!");
    	userIndex = uList.getUserIndex(user);
    	albums = uList.getList().get(uList.getUserIndex(user)).getAlbumList();
    	obsList = FXCollections.observableArrayList(albums.getList());
    	obsList.sort((a,b) -> a.compareTo(b));
    	listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
		
    	logoutButton.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			newStage.close();
    			oldStage.show();
    		}
    	});
    	
    	deleteAlbum.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!listView.getSelectionModel().isEmpty()) {
    				int sel = listView.getSelectionModel().getSelectedIndex();
    				uList.getList().get(userIndex).getAlbumList().deleteAlbum(listView.getSelectionModel().getSelectedItem());
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
    		}
    	});
    	
    	save.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (rename) {
    				int index = uList.getList().get(userIndex).getAlbumList().getAlbumIndex(listView.getSelectionModel().getSelectedItem());
					uList.getList().get(userIndex).getAlbumList().getAlbum(index).rename(albumTextField.getText());
					update();
					listView.getSelectionModel().select(index);
    				rename = false;
    				cancel.fire();
    			}
    			else if (!albumTextField.getText().isBlank()) {
    				Album a = new Album(albumTextField.getText());
    				if (!albums.getList().contains(a)) {
    					uList.getList().get(userIndex).getAlbumList().addAlbum(a);
    					update();
    					listView.getSelectionModel().select(albums.getList().get(albums.getAlbumIndex(a)));
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
    }
    
    public void update() {
		try {
			UserList.writeList(uList);
			uList = UserList.readList();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		albums = uList.getList().get(userIndex).getAlbumList();
		obsList = FXCollections.observableArrayList(albums.getList());
		obsList.sort((a,b) -> a.compareTo(b));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
    }
    

}
