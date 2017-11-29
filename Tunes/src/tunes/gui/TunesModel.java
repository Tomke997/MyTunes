/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import tunes.be.Songs;
import tunes.bll.BllManager;

/**
 *
 * @author Pepe15224
 */
public class TunesModel {
    private boolean isPlaying=false;
    private MediaPlayer mediaplayer;
    private String filePath;
    private BllManager manager = new BllManager();
    private Parent root;
    private Stage stage = new Stage(); 
    
    public void playSelectedSong(Songs selectedSong)
    { 
        if(isPlaying == true)
        {
        mediaplayer.stop();
        isPlaying=false;
        }
        if(isPlaying == false)
        {
        File file = new File(selectedSong.getPath());
        filePath = file.toURI().toString();
        Media media = new Media(filePath);
        mediaplayer = new MediaPlayer(media);
        isPlaying=true;
        mediaplayer.play();    
        }      
    }
    
    public List<Songs> getAllSongs() throws SQLException
    {
      return manager.getAllSongs();
    }
    
    public void addSong(Songs song)
{
    manager.addSong(song);
}
    
    public void closeWindow(Button button)
    {
       Stage stage = (Stage) button.getScene().getWindow();
       stage.close();
    }
    
    public void popUpWindow(String path, String title,TunesViewController parent) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
        Object root = loader.load();
        TunesNewSongController controller = loader.getController();
        controller.setParent(parent);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setTitle(title);
        stage.setScene(new Scene((Parent) root));
        stage.showAndWait();
    }
    
    public void remove(Songs selectedSong)
    {
        manager.remove(selectedSong);
    }
}
