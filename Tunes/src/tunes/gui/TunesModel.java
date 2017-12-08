/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.gui;

import java.io.File;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import tunes.be.Playlists;
import tunes.be.Songs;
import tunes.be.SongsInPlaylist;
import tunes.bll.BllManager;

/**
 *
 * @author Pepe15224
 */
public class TunesModel {
    private int isPlaying=0;
    private MediaPlayer mediaplayer;
    private String filePath;
    private BllManager manager = new BllManager();
    private ObservableList<SongsInPlaylist> songInP = FXCollections.observableArrayList();
    private ObservableList<Songs> someSongs = FXCollections.observableArrayList();
    private ObservableList<Songs> allSongs = FXCollections.observableArrayList();
    private ObservableList<Playlists> allPlaylists = FXCollections.observableArrayList();
    public void playSelectedSong(Songs selectedSong)
    { 
        switch(isPlaying)
        {
            case 1:
        mediaplayer.stop();
        isPlaying=0;
        break;
            case 0:
               File file = new File(selectedSong.getPath());
        filePath = file.toURI().toString();
        Media media = new Media(filePath);
        mediaplayer = new MediaPlayer(media);
        isPlaying=1;
        mediaplayer.play(); 
        }
    }
    /*public void playSelectedSongInPlaylist(SongsInPlaylist songin)
    { 
        switch(isPlaying)
        {
            case 1:
        mediaplayer.pause();
        isPlaying=0;
        break;
            case 0:
               File file = new File(songin.getPath());
        filePath = file.toURI().toString();
        Media media = new Media(filePath);
        mediaplayer = new MediaPlayer(media);
        isPlaying=1;
        mediaplayer.play(); 
        }
    }
*/
    public void loadPlaylists() throws SQLException
    {
        allPlaylists.clear();
        allPlaylists.addAll(manager.getPlaylists());
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
    public ObservableList<Playlists> getPlaylists() throws SQLException
    {
        return allPlaylists;
    }
    public void addSong(Songs song)
{
    manager.addSong(song);
    allSongs.add(song);
    
}
    public void addPlaylist(Playlists playlist)
{
    manager.addPlaylist(playlist);
    allPlaylists.add(playlist);
    
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
    public void deletePlaylist(Playlists playlist)
    {
        manager.deletePlaylist(playlist);
        allPlaylists.remove(playlist);
    }
    public void deleteSongsInPlaylist(SongsInPlaylist songInPlaylist)
    {
        manager.deleteSongsInPlaylist(songInPlaylist);
        songInP.remove(songInPlaylist);
        
    }
    public ObservableList<Songs> getSongsByQuery(String part)
    {
        allSongs.setAll(manager.getSongsByQuery(part));
        return allSongs;
    }
    public ObservableList<SongsInPlaylist> getSongsById(int id)
    {
        songInP.setAll(manager.getSongsById(id));
        return songInP;
    }
    public void edit(Songs song) throws SQLException
    {
        manager.edit(song);
        loadAllSongs();
    }
    public void editPlaylist(Playlists playlist) throws SQLException
    {
        manager.editPlaylist(playlist);
        loadPlaylists();
    }
    public MediaPlayer getMediaPlayer()
    {
        return mediaplayer;
    }
    public void addSongsToPlaylist(Songs song,Playlists playlist, int listOrder)
    { 
        manager.addSongsToPlaylist(song, playlist, listOrder);
    }
    public ObservableList<SongsInPlaylist> test()
    {
    ObservableList<SongsInPlaylist> test = FXCollections.observableArrayList();
        for(Songs song : allSongs)
        {
            
            for(SongsInPlaylist sdas : songInP)
            {
               song.getId();
               sdas.getInfo();
               if(song.getId()==sdas.getInfo())
               {
                   sdas.setTitle(song.getTitle());
                   sdas.setPath(song.getPath());
                   test.add(sdas);
               }
            }
        }
        return test;
    }
}
