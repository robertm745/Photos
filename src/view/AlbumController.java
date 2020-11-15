package view;

import model.*;

import java.io.File;
import controller.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;



public class AlbumController {

    @FXML private ListView<Photo> photoListView;
    @FXML private ListView<?> tagsListView;
    @FXML private ImageView imageView;
    @FXML private Button newCaption;
    @FXML private Button newPhoto;
    @FXML private Button deletePhoto;
    @FXML private Button nextPhoto;
    @FXML private Button prevPhoto;
    @FXML private Button newTag;
    @FXML private Button deleteTag;
    @FXML private Button copyPhoto;
    @FXML private Button movePhoto;
    @FXML private Button save;
    @FXML private Button cancel;
    @FXML private Button backToAlbums;
    @FXML private Button logout;
    @FXML private Text albumName;
    @FXML private Text errorText;
    @FXML private Text captionText;
    @FXML private Text dateText;
    @FXML private Text captionLabel;
    @FXML private TextField CCMfield;
    @FXML private TextField CCMvalue;
    
    private UserList userList;
    private User user;
    private Album album;
    private PhotoList photoList;
    private ObservableList<Photo> obsList;
    private int userIndex;
    private int albumIndex;
	
    public void start(Stage newStage, Stage oldStage, Album album, User user) {
    	userList = UserList.readList();
    	this.album = album;
    	this.user = user;
    	this.userIndex = userList.getUserIndex(this.user);
    	this.albumIndex = userList.getList().get(this.userIndex).getAlbumList().getAlbumIndex(this.album);
    	this.photoList = userList.getList().get(this.userIndex).getAlbumList().getAlbum(albumIndex).getPhotoList();
    	albumName.setText(album.toString());
		newStage.setTitle("Album View");
		obsList = FXCollections.observableArrayList(photoList.getList());
		photoListView.setItems(obsList);
    	photoListView.getSelectionModel().select(0);
    	updatePhotoListView();
    	
		photoListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> updatePhotoListView());
    	
    	backToAlbums.setOnAction(e -> {newStage.close(); oldStage.show();});
    	
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
    	
    	deletePhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			int index = photoListView.getSelectionModel().getSelectedIndex();
    			if (index != -1) {
        			photoList.deletePhoto(photoListView.getSelectionModel().getSelectedItem());
        			saveData();
        			if (index == photoList.getList().size())
        				index--;
        			photoListView.getSelectionModel().select(index);
        			updatePhotoListView();
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
    				Photo p = new Photo(new Image(file.toURI().toString()));
    				p.setHashCode(file.toURI().toString());
    				photoList.addPhoto(p);
    				saveData();
    				photoListView.getSelectionModel().select(photoList.getList().size()-1);
    				updatePhotoListView();
    			}
    		}
    	});
    	
    	newCaption.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!photoListView.getSelectionModel().isEmpty()) {
	    			newCaption.setDisable(true);
	    			save.setVisible(true);
	    			cancel.setVisible(true);
	    			CCMfield.setVisible(true);
	    			if (photoListView.getSelectionModel().getSelectedItem().getCaption() != null)
	    				CCMfield.setText(photoListView.getSelectionModel().getSelectedItem().getCaption());
	    			newTag.setVisible(false);
	    			deleteTag.setVisible(false);
	    			newPhoto.setVisible(false);
	    			deletePhoto.setVisible(false);
	    			copyPhoto.setVisible(false);
	    			movePhoto.setVisible(false);
	    			nextPhoto.setVisible(false);
	    			prevPhoto.setVisible(false);
    			}
    		}
    	});
    	
    	cancel.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			newCaption.setDisable(false);
    			save.setVisible(false);
    			cancel.setVisible(false);
    			CCMfield.setVisible(false);
    			CCMvalue.setVisible(false);
    			newTag.setVisible(true);
    			deleteTag.setVisible(true);
    			newPhoto.setVisible(true);
    			deletePhoto.setVisible(true);
    			copyPhoto.setVisible(true);
    			movePhoto.setVisible(true);
    			nextPhoto.setVisible(true);
    			prevPhoto.setVisible(true);
    		}
    	});
    	
    	save.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
	    			photoListView.getSelectionModel().getSelectedItem().setCaption(CCMfield.getText());
	    			saveData();
	    			cancel.fire();
	    			photoListView.getSelectionModel().select(photoList.getList().size()-1); 
	    			updatePhotoListView();
    		}
    	});
    	
    	nextPhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!photoListView.getSelectionModel().isEmpty()) {
        			int ind = photoListView.getSelectionModel().getSelectedIndex() + 1;
        			if (ind == photoList.getList().size()) {
        				ind = 0;
        			}
        			photoListView.getSelectionModel().select(ind);
    			}
    		}
    	});
    	
    	prevPhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!photoListView.getSelectionModel().isEmpty()) {
        			int ind = photoListView.getSelectionModel().getSelectedIndex() - 1;
        			if (ind == -1) {
        				ind = photoList.getList().size() - 1;
        			}
        			photoListView.getSelectionModel().select(ind);
    			}
    		}
    	});
    	
    }
    
    public void saveData() {
		UserList.writeList(userList);
		userList = UserList.readList();
		this.photoList = userList.getList().get(userIndex).getAlbumList().getAlbum(albumIndex).getPhotoList();
		obsList = FXCollections.observableArrayList(this.photoList.getList());
		//obsList.sort((a,b) -> a.compareTo(b));
		photoListView.setItems(obsList);
		photoListView.requestFocus();
    }

    public void updatePhotoListView() {
		if (photoListView.getSelectionModel().getSelectedIndex() != -1) {
			Photo ph = photoListView.getSelectionModel().getSelectedItem();
			imageView.setImage(ph.getImage());
			//imageView.setFitHeight(239);
			//imageView.setFitWidth(413);
			
			if (ph.getCaption() != null && !ph.getCaption().isBlank()) {
				captionLabel.setVisible(true);
				captionText.setVisible(true);
				captionText.setText(ph.getCaption());
			}
			else {
				captionLabel.setVisible(false);
				captionText.setVisible(false);
			}					
		} 
		else {
			imageView.setImage(null);
			captionLabel.setVisible(false);
			captionText.setVisible(false);
		}
    }
}
