package view;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import model.UserList;

public class AlbumController {

	@FXML
	private ListView<Photo> photoListView;
	@FXML
	private ListView<?> tagsListView;
	@FXML
	private ImageView imageView;
	@FXML
	private Button newCaption;
	@FXML
	private Button newPhoto;
	@FXML
	private Button deletePhoto;
	@FXML
	private Button nextPhoto;
	@FXML
	private Button prevPhoto;
	@FXML
	private Button newTag;
	@FXML
	private Button deleteTag;
	@FXML
	private Button copyPhoto;
	@FXML
	private Button movePhoto;
	@FXML
	private Button save;
	@FXML
	private Button cancel;
	@FXML
	private Button backToAlbums;
	@FXML
	private Button logout;
	@FXML
	private Text albumName;
	@FXML
	private Text errorText;
	@FXML
	private Text captionText;
	@FXML
	private Text dateText;
	@FXML
	private Text captionLabel;
	@FXML
	private TextField CCMfield;
	@FXML
	private TextField CCMvalue;

	Album album;
	User user;
	UserList userlist;
	private ObservableList<Photo> obsList;

	public void start(Stage primaryStage) {

		albumName.setText(user + "'s album: " + album);

		obsList = FXCollections.observableArrayList(album.getPhotos());
		photoListView.setItems(obsList);
		
		photoListView.setCellFactory(param -> new ListCell<Photo>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo photo, boolean empty) {
                super.updateItem(photo, empty);
                if (empty) {
                	setText(null);
                    setGraphic(null);
                } else {
                    setText(photo.getCaption());
                    try {
						imageView.setImage(new Image(new FileInputStream(photo.getLocation()), 100, 200, true, false));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
                    setGraphic(imageView);
                }}
        });
		
		
		photoListView.getSelectionModel().select(0);
		photoListView.requestFocus();

		logout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					// Load second scene
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/Login.fxml"));
					Parent root = loader.load();

					// Get controller of scene2
					LoginController loginController = loader.getController();
					loginController.transferMessage(userlist);
					loginController.start(primaryStage);
					primaryStage.setScene(new Scene(root, 990, 770));
					primaryStage.setTitle("Photos login");
					primaryStage.show();
				} catch (IOException ex) {
					System.err.println(ex);
				}
			}

		});
		
		newPhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			FileChooser fileChooser = new FileChooser();
    			fileChooser.getExtensionFilters().addAll( new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif"));
     			File file = fileChooser.showOpenDialog(new Stage());
    			if (file != null) {
    				Photo photo = new Photo(file);    
    				obsList.add(photo);
    				album.addPhoto(photo);    				
    				photoListView.getSelectionModel().select(photo);
    				
    				try {
						UserList.write(userlist);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
    			}
    		}
    	});
		
		
		deletePhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			int index = photoListView.getSelectionModel().getSelectedIndex();
    			obsList.remove(photoListView.getSelectionModel().getSelectedItem());
    			album.deletePhoto(photoListView.getSelectionModel().getSelectedItem());
    			
    			if (obsList.size() != 0) {
    				/* after we delete song toDeleteIndex becomes the index of next song */
    				photoListView.getSelectionModel().select(index);
    				errorText.setFill(Color.BLACK);
    				errorText.setText("Deleted!");
    			} else {
    				errorText.setFill(Color.BLACK);
    				errorText.setText("Empty!");
    			}
    			
    		}
    	});
		
		
	}

	public void transferMessage(UserList userlist, User user, Album album) {
		// Display the message
		this.userlist = userlist;
		this.album = album;
		this.user = user;
	}

}
