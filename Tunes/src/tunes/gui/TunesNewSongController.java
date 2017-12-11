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
      

    @FXML
    private void cancel(ActionEvent event) {
        model.closeWindow(cancelButton);
    }
    public void setModelAndSong(TunesModel model, Songs selectedSong) {
        this.model=model;
        this.selectedSong=selectedSong;
        fillArea();
    }

    @FXML
    private void save(ActionEvent event) throws IOException, SQLException {
        if(selectedSong !=null)
        {
           selectedSong.setTitle(txtTitle.getText());
           selectedSong.setArtist(txtArtist.getText());
           selectedSong.setCategory(categoryBox.getSelectionModel().getSelectedItem());
           selectedSong.setDuration(Float.valueOf(txtTime.getText()));
           selectedSong.setPath(txtFile.getText());
           
           model.edit(selectedSong);
        }
        if(selectedSong==null)
        {
        model.addSong(new Songs(-1, txtTitle.getText(),
                txtArtist.getText(),categoryBox.getSelectionModel().getSelectedItem(),
                Float.valueOf(txtTime.getText()), txtFile.getText()));
        }
        
        model.closeWindow(saveButton);
    }

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
    @FXML
    private void more(ActionEvent event) 
    {
        categoryBox.getItems().setAll("Rock","Pop","Hip-Hop","Electronic","Jazz","Reggae","Blues","Country","Folk");
    }
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
