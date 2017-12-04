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
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tunes.be.Playlists;
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
    private int change=0;
     
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
    @FXML
    private TextField txtSearch;
    @FXML
    private Button searchButton;
    @FXML
    private ImageView searchImage;
    private ImageView SearchNotHot;
    @FXML
    private ImageView searchNotHot;
    private int isPlaying=0;
    @FXML
    private ImageView imagePlay;
    @FXML
    private ImageView imageStop;
    @FXML
    private Button newPlaylist;
    @FXML
    private Button editPlaylist;
    @FXML
    private Button deletePlaylist;
    @FXML
    private TableView<Playlists> playlistList;
    @FXML
    private TableColumn<Playlists, String> columnName;
    @FXML
    private TableColumn<Playlists, Integer> columnSongs;
    @FXML
    private TableColumn<Playlists, String> columnDuration;
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       
       columnTitle.setCellValueFactory(new PropertyValueFactory("title"));
       columnArtist.setCellValueFactory(new PropertyValueFactory("artist"));
       columnCategory.setCellValueFactory(new PropertyValueFactory("category"));
       columnTime.setCellValueFactory(new PropertyValueFactory("duration")); 
       columnName.setCellValueFactory(new PropertyValueFactory("name"));
       columnSongs.setCellValueFactory(new PropertyValueFactory("songs"));
       columnDuration.setCellValueFactory(new PropertyValueFactory("time"));

        try {
            model.loadPlaylists();
            model.loadAllSongs();
            playlistList.setItems(model.getPlaylists());
            songsTable.setItems(model.getAllSongs());
        } catch (SQLException ex) {
            Logger.getLogger(TunesViewController.class.getName()).log(Level.SEVERE, null, ex);
        }  
         volume();
    }    

    @FXML
    private void play(ActionEvent event) throws SQLException {
        Songs selectedSong = songsTable.getSelectionModel().getSelectedItem();
         model.playSelectedSong(selectedSong); 
         switch(isPlaying)
         {
             case 0:
                 imagePlay.setVisible(false);
                 imageStop.setVisible(true);
                 isPlaying=1;
                 break;
             case 1:
                 imagePlay.setVisible(true);
                 imageStop.setVisible(false);
                 isPlaying=0;
                 break;
         }
    }

    @FXML
    private void previous(ActionEvent event) {
        songsTable.getSelectionModel().selectPrevious();    
    }

    @FXML
    private void next(ActionEvent event) {
     songsTable.getSelectionModel().selectNext();
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
            controller.setModelAndSong(model, null);
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
        try {
            Parent root;
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("TunesNewSong.fxml"));
            root = loader.load();
            TunesNewSongController controller = loader.getController();
            Songs selectedSong = songsTable.getSelectionModel().getSelectedItem();
            controller.setModelAndSong(model, selectedSong);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("New/Edit Song");
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException ex) {
            Logger.getLogger(TunesViewController.class.getName()).log(Level.SEVERE, null, ex);
        }
   
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

    @FXML
    private void search(ActionEvent event) throws SQLException {
       switch(change)
       {
        case 0:
        {
         searchNotHot.setVisible(true);
         searchImage.setVisible(false);
       songsTable.setItems(model.getSongsByQuery(txtSearch.getText()));  
        change++;   
        }
        break;
        case 1:
        {
       searchNotHot.setVisible(false);
       searchImage.setVisible(true);
       model.loadAllSongs();
       songsTable.setItems(model.getAllSongs());
       change--;
        }
           break;
       }
       
    }
private final void volume()
{
    slider.setValue(100);
    slider.valueProperty().addListener(new InvalidationListener() {
        @Override
        public void invalidated(Observable observable) {
            model.getMediaPlayer().setVolume(slider.getValue()/100);
        }
    });
}

    @FXML
    private void newPlaylist(ActionEvent event) {
    }

    @FXML
    private void editPlaylist(ActionEvent event) {
    }

    @FXML
    private void deletePlaylist(ActionEvent event) {
    }
   
}
