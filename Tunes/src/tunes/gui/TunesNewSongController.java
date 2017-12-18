/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import tunes.be.Songs;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class TunesNewSongController implements Initializable {

    @FXML
    private Button cancelButton;
    @FXML
    private TextField txtTitle;
    @FXML
    private TextField txtArtist;
    @FXML
    private TextField txtTime;
    @FXML
    private TextField txtFile;
    @FXML
    private Button saveButton;
    private TunesModel model;
    @FXML
    private ComboBox<String> categoryBox;
    @FXML
    private Button chooseButton;
    private String filePath;
    @FXML
    private Button moreButton;
    private Songs selectedSong;

    /**
     * Initializes the controller class.
     */
      
/*
    closes window when button is pressed
    */
    @FXML
    private void cancel(ActionEvent event) {
        model.closeWindow(cancelButton);
    }
     /*
    takes model and selected song and also fills area
    */
    public void setModelAndSong(TunesModel model, Songs selectedSong) {
        this.model=model;
        this.selectedSong=selectedSong;
        fillArea();
    }
/*
    adds new song to database based on the text fields when selected song is null
    otherwise it edits selected song based on the text fields
    */
    @FXML
    private void save(ActionEvent event) throws IOException, SQLException {
        if(selectedSong !=null)
        {
           selectedSong.setTitle(txtTitle.getText());
           selectedSong.setArtist(txtArtist.getText());
           selectedSong.setCategory(categoryBox.getSelectionModel().getSelectedItem());
           selectedSong.setDuration(Double.valueOf(txtTime.getText()));
           selectedSong.setPath(txtFile.getText());
           
           model.edit(selectedSong);
        }
        if(selectedSong==null)
        {
        model.addSong(new Songs(-1, txtTitle.getText(),
                txtArtist.getText(),categoryBox.getSelectionModel().getSelectedItem(),
                Double.valueOf(txtTime.getText()), txtFile.getText()));
        }
        
        model.closeWindow(saveButton);
    }
/*
    creates file chooser that fills text field with path to the chosen file
    */
    @FXML
    private void chooseLocation(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter filterMp3 = new FileChooser.ExtensionFilter("select mp3","*mp3");
        FileChooser.ExtensionFilter filterWav = new FileChooser.ExtensionFilter("select wav","*wav");
        fileChooser.getExtensionFilters().addAll(filterMp3,filterWav);
        File file = fileChooser.showOpenDialog(null);
        filePath = file.toString();
        
        txtFile.setText(filePath);
    }
   /*
    button that fills combobox with other song types
    */
    @FXML
    private void more(ActionEvent event) 
    {
        categoryBox.getItems().setAll("Rock","Pop","Hip-Hop","Electronic","Jazz","Reggae","Blues","Country","Folk");
    }
   /*
    fills text fields with informations about selected song
    */
    private void fillArea()
    {
        
        if(selectedSong != null)
        {
        txtTitle.setText(selectedSong.getTitle());
        txtArtist.setText(selectedSong.getArtist());
        txtTime.setText(""+selectedSong.getDuration());
        txtFile.setText(selectedSong.getPath());
       
        categoryBox.getSelectionModel().select(selectedSong.getCategory());
        }      
    }
   
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        categoryBox.getItems().setAll("Rock","Pop","Hip-Hop");
        categoryBox.getSelectionModel().selectFirst();      
    }  
}
