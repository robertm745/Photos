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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.*;

public class NonAdminController {
	@FXML
	private ListView<Album> listView;
	@FXML
	private Text promptText;
	@FXML
	private Button search;
	@FXML
	private Button createAlbum;
	@FXML
	private Button renameAlbum;
	@FXML
	private Button deleteAlbum;
	@FXML
	private Button openAlbum;
	@FXML
	private Button logoutButton;
	@FXML
	private TextField albumTextField;
	@FXML
	private Text errorText;
	@FXML
	private Button cancel;
	@FXML
	private Button save;

//	private AlbumList albums;
	private UserList userlist;
	private User user;
	private String mode = "";
	private ObservableList<Album> obsList;

	public void start(Stage primaryStage) {

		promptText.setText("Welcome " + user + "!");

		obsList = FXCollections.observableArrayList(user.getAlbums());
		listView.setItems(obsList);
		listView.getSelectionModel().select(0);
		listView.requestFocus();

		logoutButton.setOnAction(new EventHandler<ActionEvent>() {
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
					primaryStage.show();
				} catch (IOException ex) {
					System.err.println(ex);
				}
			}

		});

		createAlbum.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				errorText.setText("");
				errorText.setFill(Color.BLACK);
				listView.setDisable(true);
				albumTextField.setVisible(true);
				cancel.setVisible(true);
				save.setVisible(true);
				deleteAlbum.setVisible(false);
				renameAlbum.setVisible(false);
				createAlbum.setVisible(false);
				openAlbum.setVisible(false);
				search.setVisible(false);
				mode = "create";
			}
		});

		save.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (mode.equals("create")) {
					if (!albumTextField.getText().strip().isEmpty()) {
						Album newAlbum = new Album(albumTextField.getText());
						if (user.contains(newAlbum)) {
							errorText.setFill(Color.RED);
							errorText.setText("Error: Duplicate!");
						} else {
							obsList.add(newAlbum);
							user.addAlbum(newAlbum);

							errorText.setFill(Color.BLACK);
							errorText.setText("Added!");
							listView.getSelectionModel().select(newAlbum);

							reset();

						}
					} else {
						errorText.setFill(Color.RED);
						errorText.setText("Error: Name and artist required!");
					}
				}
			}
		});

		cancel.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				// TODO Auto-generated method stub
				albumTextField.setVisible(false);
				albumTextField.clear();
				cancel.setVisible(false);
				save.setVisible(false);
				deleteAlbum.setVisible(true);
				renameAlbum.setVisible(true);
				createAlbum.setVisible(true);
				openAlbum.setVisible(true);
				search.setVisible(true);
				listView.setDisable(false);
				listView.requestFocus();
			}

		});

		deleteAlbum.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (!listView.getSelectionModel().isEmpty()) {
					int index = listView.getSelectionModel().getSelectedIndex();
					user.deleteAlbum(listView.getSelectionModel().getSelectedItem());
					if (index == obsList.size())
						index--;
					listView.getSelectionModel().select(index);
					obsList.remove(index);
					errorText.setText("Deleted!");
					errorText.setFill(Color.BLACK);

				}
			}
		});

		openAlbum.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				try {
					// Load second scene
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/album.fxml"));
					Parent root = loader.load();

					// Get controller of scene2
					AlbumController albumController = loader.getController();
					albumController.transferMessage(userlist, user, listView.getSelectionModel().getSelectedItem());
					albumController.start(primaryStage);
					primaryStage.setScene(new Scene(root, 990, 770));
					primaryStage.show();
				} catch (IOException ex) {
					System.err.println(ex);
				}
			}
		});

	}

	public void reset() {
		albumTextField.setVisible(false);
		albumTextField.clear();
		cancel.setVisible(false);
		save.setVisible(false);
		deleteAlbum.setVisible(true);
		renameAlbum.setVisible(true);
		createAlbum.setVisible(true);
		openAlbum.setVisible(true);
		search.setVisible(true);
		listView.setDisable(false);
		listView.requestFocus();
	}

	public void transferMessage(UserList userlist, User user) {
		// Display the message
		this.userlist = userlist;
		this.user = user;
	}
}
