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
import javafx.scene.control.TextField;
import tunes.be.Playlists;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class TunesNewPlayListController implements Initializable {

    @FXML
    private Button btSave;
    @FXML
    private Button btCancel;
    @FXML
    private TextField textBar;
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
    takes model and selected song or model and name when selected playlist is null
    */
public void setModelAndPlaylist(TunesModel model, Playlists selectedPlaylist) {
        this.model=model;
        this.selectedPlaylist=selectedPlaylist;
        if(selectedPlaylist!=null)
       textBar.setText(selectedPlaylist.getName());
    }
   /*
  it adds new playlist to database when the selectedPlaylist is null
or it edits selected playlist when the button is pressed
    */
@FXML
    private void Save(ActionEvent event) throws SQLException {
        if(selectedPlaylist!=null)
        {
        selectedPlaylist.setName(textBar.getText());
        model.editPlaylist(selectedPlaylist);
        }
        if(selectedPlaylist==null)
        {
        model.addPlaylist(new Playlists(-1, textBar.getText(), 0d, 0));
        }
        model.closeWindow(btSave);
        
    }
/*
    closes window when button is pressed
    */
    @FXML
    private void Cancel(ActionEvent event) {
        model.closeWindow(btCancel);
    }   
}
