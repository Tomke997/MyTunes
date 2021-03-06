/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import tunes.be.Playlists;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class TunesDeletePlayListController implements Initializable {

    @FXML
    private Button btYes;
    @FXML
    private Button btNo;
    private Playlists selectedPlaylist;
    private TunesModel model;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
/*
    closes window and deletes playlist when yes button is pressed
    */
    @FXML
    private void Yes(ActionEvent event) throws SQLException {
        model.deletePlaylist(selectedPlaylist);
        model.closeWindow(btYes);
    }
/*
    closes window when no butoon is pressed
    */
    @FXML
    private void No(ActionEvent event) {
        model.closeWindow(btNo);
    }
    /*
    takes model and selected playlist
    */
    public void setModelAndPlaylist(TunesModel model, Playlists selectedPlaylist) {
        this.model=model;
        this.selectedPlaylist=selectedPlaylist;
    }
}
