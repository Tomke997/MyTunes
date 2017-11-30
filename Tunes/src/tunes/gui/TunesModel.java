/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
    private ObservableList<Songs> allSongs = FXCollections.observableArrayList();
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
    
    public void loadAllSongs() throws SQLException
    {
        allSongs.clear();
        allSongs.addAll(manager.getAllSongs());
    }
    public ObservableList<Songs> getAllSongs() throws SQLException
    {
        return allSongs;
   
    }
    public void addSong(Songs song)
{
    manager.addSong(song);
    allSongs.add(song);
    
}
    public void closeWindow(Button button)
    {
        Stage stage = (Stage) button.getScene().getWindow();
       stage.close();
    }
    public void delete(Songs selectedSong)
    {
        manager.delete(selectedSong);
        allSongs.remove(selectedSong);
    }
    public ObservableList<Songs> getSongsByQuery(String part)
    {
        allSongs.setAll(manager.getSongsByQuery(part));
        return allSongs;
    }
}
