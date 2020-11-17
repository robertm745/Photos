package view;

import model.*;
import controller.Photos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchController {

    @FXML private RadioButton searchByDate;
    @FXML private RadioButton searchByTags;
    @FXML private RadioButton andRadio;
    @FXML private RadioButton orRadio;
    @FXML private TextField tagFieldTwo;
    @FXML private TextField tagFieldOne;
    @FXML private Button search;
    @FXML private Button backToAlbums;
    @FXML private Button logout;
    @FXML private DatePicker datePickFrom;
    @FXML private DatePicker datePickTo;

    private UserList userList;
    
    public void start(Stage newStage, Stage oldStage, User user, NonAdminController nac) {
    	
    	this.userList = UserList.readList();
    	
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
    			andRadio.setSelected(true);
    		}
    	});
    	
    	orRadio.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			tagRadioState(true);
    		}
    	});
    	
    	andRadio.setOnAction(new EventHandler<ActionEvent>() {
    		@Override
    		public void handle(ActionEvent e) {
    			tagRadioState(false);
    		}
    	});
    	

    	
    }
    
    public void changeState(boolean value) {
    	tagFieldOne.setVisible(!value);
    	tagFieldTwo.setVisible(!value);
    	andRadio.setVisible(!value);
    	orRadio.setVisible(!value);
    	datePickFrom.setVisible(value);
    	datePickTo.setVisible(value);
    	searchByDate.setSelected(value);
    	searchByTags.setSelected(!value);
    }
    
    public void tagRadioState(boolean value) {
		andRadio.setSelected(!value);
		orRadio.setSelected(value);
    }

}

