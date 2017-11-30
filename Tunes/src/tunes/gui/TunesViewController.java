/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tunes.be.Songs;

/**
 * FXML Controller class
 *
 * @author Pepe15224
 */
public class TunesViewController implements Initializable {

    @FXML
    private TableColumn<Songs, String> columnTitle;
    @FXML
    private TableColumn<Songs, String> columnArtist;
    @FXML
    private TableColumn<Songs, String> columnCategory;
    @FXML
    private TableColumn<Songs, String> columnTime;
    @FXML
    private TableView<Songs> songsTable;
    private TunesModel model = new TunesModel();
     
    @FXML
    private Button next;
    @FXML
    private Slider slider;
    @FXML
    private Button previous;
    @FXML
    private Button play;
    @FXML
    private Button closeButton;
    @FXML
    private Button newButton;
    @FXML
    private Button editButton;
    @FXML
    private Button deleteButton;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       columnTitle.setCellValueFactory(new PropertyValueFactory("title"));
       columnArtist.setCellValueFactory(new PropertyValueFactory("artist"));
       columnCategory.setCellValueFactory(new PropertyValueFactory("category"));
       columnTime.setCellValueFactory(new PropertyValueFactory("duration")); 
 
       
        try {
            model.loadAllSongs();
            songsTable.setItems(model.getAllSongs());
        } catch (SQLException ex) {
            Logger.getLogger(TunesViewController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }    

    @FXML
    private void play(ActionEvent event) throws SQLException {
        Songs selectedSong = songsTable.getSelectionModel().getSelectedItem();
        model.playSelectedSong(selectedSong);   
    }

    @FXML
    private void previous(ActionEvent event) {
        songsTable.getSelectionModel().selectPrevious();
        Songs selectedSong = songsTable.getSelectionModel().getSelectedItem();
        model.playSelectedSong(selectedSong);
    }

    @FXML
    private void next(ActionEvent event) {
     songsTable.getSelectionModel().selectNext();
     Songs selectedSong = songsTable.getSelectionModel().getSelectedItem();
     model.playSelectedSong(selectedSong);
    }

    @FXML
    private void close(ActionEvent event) {
        model.closeWindow(closeButton);
    }

    @FXML
    private void newSongs(ActionEvent event) {  
        try {
            Parent root;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TunesNewSong.fxml"));
            root = loader.load();
            TunesNewSongController controller = loader.getController();
            controller.setModel(model);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New/Edit Song");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(TunesViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void editSongs(ActionEvent event) {
    }

    @FXML
    private void deleteSongs(ActionEvent event) {
        try {
            Parent root;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TunesDeleteSong.fxml"));
            root = loader.load();
            TunesDeleteSongController controller = loader.getController();
            Songs selectedSong = songsTable.getSelectionModel().getSelectedItem();
      
            controller.setModelAndSong(model, selectedSong);
            
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Are you sure");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(TunesViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
   
}
