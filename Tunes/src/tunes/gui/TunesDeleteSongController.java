/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import tunes.be.Songs;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class TunesDeleteSongController implements Initializable {

    @FXML
    private Button yesButton;
    @FXML
    private Button noButton;
    private TunesModel model;
    private Songs selectedSong;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
/*
    closes window and deletes song when yes button is pressed
    */
    @FXML
    private void yes(ActionEvent event) {
        model.delete(selectedSong);
        model.closeWindow(yesButton);       
    }
/*
    closes window when no button is pressed
    */
    @FXML
    private void no(ActionEvent event) {
        model.closeWindow(noButton);
    }
 /*
    takes model and selected song
    */
    void setModelAndSong(TunesModel model, Songs selectedSong) {
        this.model=model;
        this.selectedSong=selectedSong;
    }   
}
