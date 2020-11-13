package view;

import model.*;

import java.io.IOException;

import controller.Photos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AlbumController {

    @FXML private ListView<?> photosList;
    @FXML private ListView<?> tagsList;
    @FXML private Button newPhoto;
    @FXML private Button deletePhoto;
    @FXML private Button caption;
    @FXML private ImageView imageView;
    @FXML private Button nextPhoto;
    @FXML private Button prevPhoto;
    @FXML private Button newTag;
    @FXML private Button deleteTag;
    @FXML private Button copyPhoto;
    @FXML private Button movePhoto;
    @FXML private TextField tagValueField;
    @FXML private Text errorText;
    @FXML private Button addTag;
    @FXML private Button cancel;
    @FXML private Text albumName;
    @FXML private Button backToAlbums;
    @FXML private Button logout;
    @FXML private Text captionText;
    @FXML private Text dateText;
    @FXML private TextField tagNameField;
    
    private UserList userList;
    private User user;
    private Album album;
	
    public void start(Stage newStage, Stage oldStage, Album album, User user) {
    	try {
			userList = UserList.readList();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
    	this.album = album;
    	this.user = user;
    	albumName.setText(album.toString());
		newStage.setTitle("Album View");
    	
    	backToAlbums.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			newStage.close();
    			oldStage.show();
    		}
    	});
    	
    	logout.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			newStage.close();
    			oldStage.close();
    			try {
					new Photos().start(new Stage());
				} catch (Exception e1) {
					e1.printStackTrace();
				}
    		}
    	});
    	
    }

}
