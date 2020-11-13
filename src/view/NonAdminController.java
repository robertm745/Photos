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
    private ObservableList<Album> obsList;
    private UserList uList;
    
    public void start(Stage newStage, Stage oldStage, User user) {
    	try {
			this.uList = UserList.readList();
		} catch (ClassNotFoundException | IOException e2) {
			e2.printStackTrace();
		}
    	promptText.setText("Welcome, " + user + "!");
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
    				int index = listView.getSelectionModel().getSelectedIndex();
    				Album al = listView.getSelectionModel().getSelectedItem();
    				int index2 = uList.getUserIndex(user);
    				uList.getList().get(index2).getAlbumList().deleteAlbum(al);
    				update(index2);
    				if (index == albums.getList().size()) 
    					index--;
    				listView.getSelectionModel().select(index);
    			}
    		}
    	});
    }
    
    public void update(int index) {
		try {
			UserList.writeList(uList);
			uList = UserList.readList();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		obsList = FXCollections.observableArrayList(uList.getList().get(index).getAlbumList().getList());
		obsList.sort((a,b) -> a.compareTo(b));
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();
    }
    

}
