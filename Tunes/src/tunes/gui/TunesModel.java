/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.io.File;
import java.sql.SQLException;
import java.util.List;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
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
}
