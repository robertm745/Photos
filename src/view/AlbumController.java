package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Album;
import model.Photo;

public class AlbumController {
	Album album;
	private ObservableList<Photo> obsList;

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

	public void start(Stage primaryStage) {

		obsList = FXCollections.observableArrayList(album.getPhotos());
		photoListView.setItems(obsList);
		photoListView.getSelectionModel().select(0);
		photoListView.requestFocus();

	}

	public void transferMessage(Album album) {
		// Display the message
		this.album = album;
	}

}
