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



public class AlbumController {

    @FXML private ListView<Photo> photoListView;
    @FXML private ListView<String> tagsListView;
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
    @FXML private TextField tagtypeField;
    @FXML private TextField tagvalueField;
    @FXML private ComboBox<Album> albumCB;
    @FXML private ComboBox<String> tagCB;
    
    private UserList userList;
    private User user;
    private Album album;
    private ObservableList<Photo> obsList;
    private ObservableList<String> tagobsList;
    private int userIndex;
    private int albumIndex;
    private boolean captionState;
    private boolean copyState;
    private boolean tagState;
	
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
    				saveData();
    				photoListView.getSelectionModel().select(p);
    				updatePhotoListView(photoListView.getSelectionModel().getSelectedIndex());
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
    			albumCB.setVisible(false);     
    			tagCB.setVisible(false);
    			tagtypeField.setVisible(false);
    			tagvalueField.setVisible(false);
    			tagtypeField.clear();
    			tagvalueField.clear();

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
    					if (tagvalueField.getText().strip().isEmpty()) {
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
    				else {    			
	    				if (albumCB.getSelectionModel().getSelectedIndex() != -1) {
	    					Album a = albumCB.getSelectionModel().getSelectedItem();
	    					Photo p = photoListView.getSelectionModel().getSelectedItem();
	    					a.addPhoto(new Photo(p.toString(), p.getDateTime().getTimeInMillis(), p.getCaption(), p.getTags()));
	        				if (!copyState) {
	        					deletePhoto.fire();
	        				}
	    				}
	    				cancel.fire();
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
    
    public void saveData() {
		UserList.writeList(userList);
		userList = UserList.readList();
		this.album = userList.getList().get(userIndex).getAlbumList().getAlbum(albumIndex);
		obsList = FXCollections.observableArrayList(this.album.getPhotos());
		obsList.sort((a,b) -> a.compareTo(b));
		photoListView.setItems(obsList);
		photoListView.requestFocus();		
    }

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
			System.out.println(index + " is index");
			imageView.setImage(null);
			captionLabel.setVisible(false);
			captionText.setVisible(false);
			dateText.setVisible(false);
		}
		photoListView.requestFocus();
    }
    
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
    }
}
