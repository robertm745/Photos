package view;

import model.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import controller.Photos;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;



// TODO: Auto-generated Javadoc
/**
 * The Class AlbumController.
 * 
 * @author Mustafa
 * @author Robert
 */
public class AlbumController {

    /** The photo list view. */
    @FXML private ListView<Photo> photoListView;
    
    /** The tags list view. */
    @FXML private ListView<String> tagsListView;
    
    /** The image view. */
    @FXML private ImageView imageView;
    
    /** The new caption. */
    @FXML private Button newCaption;
    
    /** The new photo. */
    @FXML private Button newPhoto;
    
    /** The delete photo. */
    @FXML private Button deletePhoto;
    
    /** The next photo. */
    @FXML private Button nextPhoto;
    
    /** The prev photo. */
    @FXML private Button prevPhoto;
    
    /** The new tag. */
    @FXML private Button newTag;
    
    /** The delete tag. */
    @FXML private Button deleteTag;
    
    /** The copy photo. */
    @FXML private Button copyPhoto;
    
    /** The move photo. */
    @FXML private Button movePhoto;
    
    /** The save. */
    @FXML private Button save;
    
    /** The cancel. */
    @FXML private Button cancel;
    
    /** The back to albums. */
    @FXML private Button backToAlbums;
    
    /** The logout. */
    @FXML private Button logout;
    
    /** The album name. */
    @FXML private Text albumName;
    
    /** The error text. */
    @FXML private Text errorText;
    
    /** The caption text. */
    @FXML private Text captionText;
    
    /** The date text. */
    @FXML private Text dateText;
    
    /** The caption label. */
    @FXML private Text captionLabel;
    
    /** The CC mfield. */
    @FXML private TextField CCMfield;
    
    /** The tagtype field. */
    @FXML private TextField tagtypeField;
    
    /** The tagvalue field. */
    @FXML private TextField tagvalueField;
    
    /** The album CB. */
    @FXML private ComboBox<Album> albumCB;
    
    /** The tag CB. */
    @FXML private ComboBox<String> tagCB;
    
    /** The user list. */
    private UserList userList;
    
    /** The user. */
    private User user;
    
    /** The album. */
    private Album album;
    
    /** The obs list. */
    private ObservableList<Photo> obsList;
    
    /** The tagobs list. */
    private ObservableList<String> tagobsList;
    
    /** The user index. */
    private int userIndex;
    
    /** The album index. */
    private int albumIndex;
    
    /** The caption state. */
    private boolean captionState;
    
    /** The copy state. */
    private boolean copyState;
    
    /** The tag state. */
    private boolean tagState;
	
    /**
     * Starts the contorller.
     *
     * @param newStage the new stage
     * @param oldStage the old stage
     * @param al the album
     * @param user the user
     * @param nac the nonadmincontroller
     */
    public void start(Stage newStage, Stage oldStage, Album al, User user, NonAdminController nac) {
    	userList = UserList.readList();
    	this.album = al;
    	this.user = user;
    	this.userIndex = userList.getUserIndex(this.user);
    	this.albumIndex = userList.getList().get(this.userIndex).getAlbumList().getAlbumIndex(this.album);
    	saveData();

    	albumName.setText(album.toString());
		newStage.setTitle("Album View");
		obsList = FXCollections.observableArrayList(this.album.getPhotos());
		obsList.sort((a,b) -> a.compareTo(b));
		photoListView.setItems(obsList);		
		photoListView.setCellFactory(param -> new ListCell<Photo>() {
            private ImageView imageView = new ImageView();
            @Override
            public void updateItem(Photo p, boolean empty) {    
                super.updateItem(p, empty);
                if (empty || p == null) {
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
    	updatePhotoListView(photoListView.getSelectionModel().getSelectedIndex());
    	
		photoListView.getSelectionModel().selectedIndexProperty().addListener((obs, oldVal, newVal) -> updatePhotoListView(photoListView.getSelectionModel().getSelectedIndex()));
    	
    	backToAlbums.setOnAction(e -> {
    		newStage.close(); 
    		oldStage.setOnShown(f -> {
    			nac.userList = this.userList; 
    			nac.saveData(); 
    			nac.updateListView();
    		}); 
    		oldStage.show();
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
    	
    	deletePhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			int index = photoListView.getSelectionModel().getSelectedIndex();
    			if (index != -1) {
    				Photo p = photoListView.getSelectionModel().getSelectedItem();
        			userList.getList().get(userIndex).getAlbumList().getAlbum(albumIndex).deletePhoto(p);
        			saveData();
        			if (index == album.getPhotos().size())
        				index--;
        			updatePhotoListView(index);
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
    				Photo p = new Photo(file.getAbsolutePath(), file.lastModified());
    				album.addPhoto(p);
    				album.getPhotos().sort((a,b) -> a.compareTo(b));
    				int index = album.getPhotos().indexOf(p);
    				saveData();
    				updatePhotoListView(index);
    			} 
    		}
    	});
    	
    	newCaption.setOnAction(e -> {
			if (!photoListView.getSelectionModel().isEmpty()) {
				changeState();
    			captionState = true;
				copyPhoto.setVisible(false);
				movePhoto.setVisible(false);
    			newCaption.setDisable(true);
    			Photo p = photoListView.getSelectionModel().getSelectedItem();
    			if (p.getCaption() != null)
    				CCMfield.setText(p.getCaption());
    			CCMfield.requestFocus();
    			CCMfield.setVisible(true);
    			save.setText("Save");
			}
    	});
    	
    	cancel.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			newCaption.setVisible(true);
    			newCaption.setDisable(false);
    			copyPhoto.setVisible(true);
    			copyPhoto.setDisable(false);
    			movePhoto.setVisible(true);
    			movePhoto.setDisable(false);
    			save.setVisible(false);
    			cancel.setVisible(false);
    			CCMfield.setVisible(false);
    			newTag.setVisible(true);
    			deleteTag.setVisible(true);
    			newPhoto.setVisible(true);
    			deletePhoto.setVisible(true);
    			copyPhoto.setVisible(true);
    			movePhoto.setVisible(true);
    			nextPhoto.setVisible(true);
    			prevPhoto.setVisible(true);
    			photoListView.setDisable(false);
    			copyState = false;
    			captionState = false;
    			tagState = false;
    			albumCB.setVisible(false);     
    			tagCB.setVisible(false);
    			tagtypeField.setVisible(false);
    			tagvalueField.setVisible(false);
    			tagtypeField.clear();
    			tagvalueField.clear();
    			errorText.setVisible(false);

    		}
    	});
    	
    	save.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (captionState) {
        			String s = CCMfield.getText();
        			if (s.length() > 30) {
        				errorText.setVisible(true);
        				errorText.setText("Error: Caption more than 30 chars");    				
        			} else {
        				photoListView.getSelectionModel().getSelectedItem().setCaption(s);
        				int index = photoListView.getSelectionModel().getSelectedIndex();
    	    			saveData();
    	    			cancel.fire();
    	    			updatePhotoListView(index);
    	    			photoListView.setDisable(false);
        			}
    			} else if (tagState) {
					int index = photoListView.getSelectionModel().getSelectedIndex();
					int tagIndex = tagCB.getSelectionModel().getSelectedIndex();
					if (tagIndex == -1) {
						errorText.setVisible(true);
        				errorText.setText("Error: please select a tag type");
					} 
					else {
	    				if (tagCB.getSelectionModel().getSelectedItem().equals("Create new tag")) {
	    					if(tagtypeField.getText().strip().isEmpty() || tagvalueField.getText().strip().isEmpty()) {
	    						errorText.setVisible(true);
	            				errorText.setText("Error: please enter tag type and value");
	    					} else {
								if (!user.getTags().contains(tagtypeField.getText())) {
								      user.addTag(tagtypeField.getText());
								}
	    						photoListView.getSelectionModel().getSelectedItem().addTag(tagtypeField.getText(), tagvalueField.getText());
	        					saveData();
	        	    			cancel.fire();
	        	    			updatePhotoListView(index);
	        	    			photoListView.setDisable(false);
	        	    			tagState = false;
	    					}			
	    					    					
	    				} else {    	
	    					if (tagvalueField.getText().strip().isEmpty() || index == -1) {
	    						errorText.setVisible(true);
	            				errorText.setText("Error: please enter tag value");
	    					} else {
	    						photoListView.getSelectionModel().getSelectedItem().addTag(tagCB.getSelectionModel().getSelectedItem(), tagvalueField.getText());
	        					saveData();
	        	    			cancel.fire();
	        	    			updatePhotoListView(index);
	        	    			photoListView.setDisable(false);
	        	    			tagState = false;
	    					}	    					
	    				}
					}
    			}
    				else {    			
	    				if (albumCB.getSelectionModel().getSelectedIndex() != -1) {
	    					Album a = albumCB.getSelectionModel().getSelectedItem();
	    					Photo p = photoListView.getSelectionModel().getSelectedItem();
	    					a.addPhoto(new Photo(p.toString(), p.getDateTime().getTimeInMillis(), p.getCaption(), p.getTags()));
	        				if (!copyState) {
	        					deletePhoto.fire();
	        				}
	        				cancel.fire();
	    				} else {
	    					errorText.setVisible(true);
	    					errorText.setText("Error: please select an album");
	    				}
    			}
    		}
    	});
    	
    	nextPhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!photoListView.getSelectionModel().isEmpty()) {
        			int ind = photoListView.getSelectionModel().getSelectedIndex() + 1;
        			if (ind == album.getPhotos().size()) {
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
        				ind = album.getPhotos().size() - 1;
        			}
        			photoListView.getSelectionModel().select(ind);
    			}
    		}
    	});
    	
    	copyPhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!photoListView.getSelectionModel().isEmpty()) {
        			copyState = true;
        			changeState();
        			copyPhoto.setDisable(true);
        			movePhoto.setVisible(false);
        			newCaption.setVisible(false);
        			albumCB.setVisible(true);
        			albumCB.setItems(FXCollections.observableArrayList(userList.getList().get(userIndex).getAlbumList().getList()));
        			albumCB.getItems().remove(album);
        			save.setText("Copy");
    			}    			
    		}
    	});
    	
    	movePhoto.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!photoListView.getSelectionModel().isEmpty()) {
    				copyPhoto.fire();
        			copyPhoto.setDisable(false);
        			copyPhoto.setVisible(false);        	
        			movePhoto.setVisible(true);
        			movePhoto.setDisable(true);
    				copyState = false;
    				save.setText("Move");
    			}    			
    		}
    	});
    	
    	newTag.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			if (!photoListView.getSelectionModel().isEmpty()) {
        			tagState = true;
        			changeState();
        			newCaption.setVisible(false);
    				copyPhoto.setVisible(false);
    				movePhoto.setVisible(false);
        			newCaption.setDisable(true); 
        			tagCB.setVisible(true);   
        			
        			tagCB.getSelectionModel().clearSelection();
        			//tagCB.getItems().clear();
        			save.setText("Add");
        			tagCB.setPromptText("Select tag type");
        			
        			ObservableList<String> list = FXCollections.observableArrayList();
        			list.add("Create new tag");
        			list.addAll(user.getTags());
        			
        			tagCB.setItems(list);
    			}

    		}
    	});
    	
    	deleteTag.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			int index = tagsListView.getSelectionModel().getSelectedIndex();
    			if (index != -1) {
    				Photo p = photoListView.getSelectionModel().getSelectedItem();
    				int index2 = photoListView.getSelectionModel().getSelectedIndex();
        			p.removeTag(tagsListView.getSelectionModel().getSelectedItem());
        			saveData();
        			if (index == p.getTagsList().size())
        				index--;
        			tagsListView.getSelectionModel().select(index);
        			updatePhotoListView(index2);
    			}
    		}
    	});
    	
    	  tagCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    		  if (tagCB.getSelectionModel().getSelectedItem() != null) {
  				if (tagCB.getSelectionModel().getSelectedItem().equals("Create new tag")) {
      				tagvalueField.setVisible(true);
      				tagtypeField.setVisible(true);
      			} else {
      				tagtypeField.setVisible(false);
      				tagvalueField.setVisible(true);
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
		this.album = userList.getList().get(userIndex).getAlbumList().getAlbum(albumIndex);
		obsList = FXCollections.observableArrayList(this.album.getPhotos());
		obsList.sort((a,b) -> a.compareTo(b));
		photoListView.setItems(obsList);
		photoListView.requestFocus();		
    }

    /**
     * Update photo list view.
     *
     * @param index the index
     */
    public void updatePhotoListView(int index) {
		if (index != -1) {
			photoListView.getSelectionModel().select(index);
			Photo ph = photoListView.getSelectionModel().getSelectedItem();
			try {
				imageView.setImage(new Image(new FileInputStream(ph.toString())));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			
			dateText.setVisible(true);
			dateText.setText(ph.getDateTime().getTime().toString());
			errorText.setVisible(false);
			
			tagobsList = FXCollections.observableArrayList(ph.getTagsList());
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
    
    /**
     * Change state to edit mode.
     */
    public void changeState() {
		newTag.setVisible(false);
		deleteTag.setVisible(false);
		newPhoto.setVisible(false);
		deletePhoto.setVisible(false);
		nextPhoto.setVisible(false);
		prevPhoto.setVisible(false);
		photoListView.setDisable(true);
		save.setVisible(true);
		cancel.setVisible(true);
		errorText.setVisible(false);
    }
}
