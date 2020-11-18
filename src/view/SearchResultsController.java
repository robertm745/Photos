package view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
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
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;
import model.User;
import model.UserList;

public class SearchResultsController {



    @FXML private ListView<Photo> photoListView;
    @FXML private ListView<String> tagsListView;
    @FXML private ImageView imageView;
    @FXML private Button save;
    @FXML private Button cancel;
    @FXML private Button backToAlbums;
    @FXML private Button logout;
    @FXML private Button newAlbum;
    @FXML private Button editSearch;
    @FXML private Text errorText;
    @FXML private Text captionText;
    @FXML private Text dateText;
    @FXML private Text captionLabel;
    @FXML private TextField albumField;
    
    
    private UserList userList;
    private User user;
    private Album album;
    private ObservableList<Photo> obsList;
    private ObservableList<String> tagobsList;
    private int userIndex;
	
    public void start(Stage newStage, Stage oldStage, Album al, User u, NonAdminController nac, SearchController sc) {
    	userList = UserList.readList();
    	this.userIndex = userList.getUserIndex(u);
    	this.user = userList.getList().get(userIndex);
    	this.album = al;
		newStage.setTitle("Album View");
		obsList = FXCollections.observableArrayList(this.album.getPhotos());
		obsList.sort((a,b) -> a.compareTo(b));
		photoListView.setItems(obsList);		
		photoListView.setCellFactory(param -> new ListCell<Photo>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo p, boolean empty) {    
                super.updateItem(p, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(p.getCaption());
                    try {
						imageView.setImage(new Image(new FileInputStream(p.toString()), 100, 100, true, false));
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
                    setGraphic(imageView);
                }
            }
        });
		

		
    	photoListView.getSelectionModel().select(0);
    	updatePhotoListView();
    	
		photoListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> updatePhotoListView());
    	
    	editSearch.setOnAction(e -> {
    		newStage.close(); 
    		oldStage.setOnShown(f -> {
    			nac.userList = this.userList; 
    			nac.saveData(); 
    			nac.updateListView();
    		}); 
    		oldStage.show();
    	});
    	
    	backToAlbums.setOnAction(e -> {
    			newStage.close();
    			sc.userList = this.userList;
    			sc.backToAlbums.fire();
    		}    			
    	);
    	
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
    	
    	newAlbum.setOnAction(e -> {
    			save.setVisible(true);
    			cancel.setVisible(true);
    			albumField.setVisible(true);
    			photoListView.setDisable(true);
    			
    		}    			
    	);
    	

    	cancel.setOnAction(e -> {
			save.setVisible(false);
			cancel.setVisible(false);
			albumField.setVisible(false);
			photoListView.setDisable(false);
			errorText.setVisible(false);
    	});
    	
    	save.setOnAction(e -> {
	    	if (!albumField.getText().isBlank()) {
				al.rename(albumField.getText());
				if (!user.getAlbumList().getList().contains(al)) {
					user.getAlbumList().addAlbum(al);
					saveData();
					cancel.fire();
				} else {
					errorText.setVisible(true);
					errorText.setText("Error: Duplicate album");
				}
			} else {
				errorText.setVisible(true);
				errorText.setText("Error: Album name required");
			}
    	});
    	

    	

    }
    
    public void saveData() {
		UserList.writeList(userList);
		userList = UserList.readList();
		obsList = FXCollections.observableArrayList(this.album.getPhotos());
		obsList.sort((a,b) -> a.compareTo(b));
		photoListView.setItems(obsList);
		photoListView.requestFocus();
		
    }

    public void updatePhotoListView() {
		if (photoListView.getSelectionModel().getSelectedIndex() != -1) {
			Photo ph = photoListView.getSelectionModel().getSelectedItem();
			try {
				imageView.setImage(new Image(new FileInputStream(ph.toString())));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}

			dateText.setVisible(true);
			dateText.setText(ph.getDateTime().getTime().toString());
			errorText.setVisible(false);
			
			tagobsList = FXCollections.observableArrayList(photoListView.getSelectionModel().getSelectedItem().getTagsList());
			tagsListView.setItems(tagobsList);
			
			if (!ph.getCaption().isBlank()) {
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
			dateText.setVisible(false);
		}
		photoListView.requestFocus();
    }
    
    public void changeState() {
		photoListView.setDisable(true);
		save.setVisible(true);
		cancel.setVisible(true);
    }
}
