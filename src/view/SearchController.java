package view;

import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Calendar;

import controller.Photos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchController.
 * 
 * @author Mustafa
 * @author Robert
 */
public class SearchController {

	/** The search by date. */
	@FXML
	private RadioButton searchByDate;
	
	/** The search by tags. */
	@FXML
	private RadioButton searchByTags;
	
	/** The single radio. */
	@FXML
	private RadioButton singleRadio;
	
	/** The and radio. */
	@FXML
	private RadioButton andRadio;
	
	/** The or radio. */
	@FXML
	private RadioButton orRadio;
	
	/** The tag field two. */
	@FXML
	private TextField tagFieldTwo;
	
	/** The tag field one. */
	@FXML
	private TextField tagFieldOne;
	
	/** The search. */
	@FXML
	private Button search;
	
	/** The back to albums. */
	@FXML
	protected Button backToAlbums;
	
	/** The logout. */
	@FXML
	private Button logout;
	
	/** The date pick from. */
	@FXML
	private DatePicker datePickFrom;
	
	/** The date pick to. */
	@FXML
	private DatePicker datePickTo;
	
	/** The error text. */
	@FXML
	private Text errorText;

	/** The user list. */
	protected UserList userList;
	
	/** The us. */
	private User us;
	
	/** The user index. */
	private int userIndex;

	/**
	 * Starts the controller.
	 *
	 * @param newStage the new stage
	 * @param oldStage the old stage
	 * @param u the user
	 * @param nac the nonadmincontroller
	 */
	public void start(Stage newStage, Stage oldStage, User u, NonAdminController nac) {
    	newStage.setTitle("Search");

		this.userList = UserList.readList();
		this.userIndex = userList.getUserIndex(u);
		this.us = userList.getList().get(userIndex);

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

		backToAlbums.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				newStage.close();
				oldStage.setOnShown(f -> {
					nac.userList = userList;
					nac.saveData();
					nac.updateListView();
				});
				oldStage.show();
			}
		});

		searchByDate.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				changeState(true);
				datePickFrom.setValue(null);
				datePickTo.setValue(null);
			}
		});

		searchByTags.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				changeState(false);
				tagFieldOne.clear();
				tagFieldTwo.clear();
				tagFieldTwo.setVisible(false);

			}
		});

		singleRadio.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				tagFieldTwo.setVisible(false);
			}
		});

		orRadio.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				tagFieldTwo.setVisible(true);
			}
		});

		andRadio.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				tagFieldTwo.setVisible(true);
			}
		});

		search.setOnAction(e -> {
			if (searchByTags.isSelected()) {
				if (tagFieldOne.getText().isEmpty()) {
					errorText.setText("Please enter values");
				} else if (!singleRadio.isSelected() && tagFieldTwo.getText().isEmpty()) {
					errorText.setText("Please enter values");
				} else {
					Album temp = new Album("results");
					String tagOne = tagFieldOne.getText();
					String[] tagOneValues = tagOne.split("=");
					String tagTwo;
					String[] tagTwoValues;

					if (singleRadio.isSelected()) {
						for (Album album : us.getAlbumList().getList()) {
							for (Photo photo : album.getPhotos()) {
								//System.out.println("tag input values: " + tagOneValues[0] + "|" + tagOneValues[1] + " and this photo tag is " + photo.getTags().get("person"));
								if (photo.getTagsList().contains(tagOneValues[0] + "|" + tagOneValues[1])) {
									temp.addPhoto(photo);
								}
							}
						}
					}

					else if (!singleRadio.isSelected()) {
						tagTwo = tagFieldTwo.getText();
						tagTwoValues = tagTwo.split("=");

						for (Album album : us.getAlbumList().getList()) {
							for (Photo photo : album.getPhotos()) {

								if (andRadio.isSelected()) {
									if (photo.getTagsList().contains(tagOneValues[0] + "|" + tagOneValues[1])
											&& photo.getTagsList()
													.contains(tagTwoValues[0] + "|" + tagTwoValues[1])) {
										temp.addPhoto(photo);
									}
								} else {
									if (photo.getTagsList().contains(tagOneValues[0] + "|" + tagOneValues[1])
											|| photo.getTagsList()
													.contains(tagTwoValues[0] + "|" + tagTwoValues[1])) {
										temp.addPhoto(photo);
									}

								}
							}

						}

					}
					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/search_results.fxml"));
					Parent root = null;
					try {
						root = (Pane) loader.load();
						SearchResultsController sc = loader.getController();
						Stage stage = new Stage();
						sc.start(stage, newStage, temp, us, nac, this);
						stage.setScene(new Scene(root, 987, 770));
						stage.setResizable(false);
						stage.show();
						newStage.hide();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}

			} else if (searchByDate.isSelected()) {
				if (datePickFrom.getValue() == null || datePickTo.getValue() == null) {
					errorText.setText("Please enter values");
				} else {
					Album temp = new Album("results");

					LocalDate from = datePickFrom.getValue();
					LocalDate to = datePickTo.getValue();

					Calendar fromdatetime = Calendar.getInstance();
					fromdatetime.set(from.getYear(), from.getMonthValue() - 1, from.getDayOfMonth());
					fromdatetime.set(Calendar.MILLISECOND, 0);

					Calendar todatetime = Calendar.getInstance();
					todatetime.set(to.getYear(), to.getMonthValue() - 1, to.getDayOfMonth());
					todatetime.set(Calendar.MILLISECOND, 0);

					for (Album album : userList.getList().get(userIndex).getAlbumList().getList()) {
						for (Photo photo : album.getPhotos()) {
							if (photo.getDateTime().compareTo(fromdatetime) > 0
									&& photo.getDateTime().compareTo(todatetime) < 0) {
								temp.addPhoto(photo);
							}
						}
					}

					FXMLLoader loader = new FXMLLoader();
					loader.setLocation(getClass().getResource("/view/search_results.fxml"));
					Parent root = null;
					try {
						root = (Pane) loader.load();
						SearchResultsController sc = loader.getController();
						Stage stage = new Stage();
						sc.start(stage, newStage, temp, us, nac, this);
						stage.setScene(new Scene(root, 987, 770));
						stage.setResizable(false);
						stage.show();
						newStage.hide();

					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		});

	}

	/**
	 * Change state to edit mode.
	 *
	 * @param value the value
	 */
	public void changeState(boolean value) {
		tagFieldOne.setVisible(!value);
		tagFieldTwo.setVisible(!value);
		singleRadio.setVisible(!value);
		andRadio.setVisible(!value);
		orRadio.setVisible(!value);
		datePickFrom.setVisible(value);
		datePickTo.setVisible(value);
		searchByDate.setSelected(value);
		searchByTags.setSelected(!value);
		errorText.setText("");

	}

}
