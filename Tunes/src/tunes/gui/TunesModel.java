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
import javafx.util.Duration;
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
    private final BllManager manager = new BllManager();
    private final ObservableList<SongsInPlaylist> songInP = FXCollections.observableArrayList();
    private final ObservableList<Songs> allSongs = FXCollections.observableArrayList();
    private final ObservableList<Playlists> allPlaylists = FXCollections.observableArrayList();
    /*
    method that plays song from the songs and also
    stops the mediaplayer if the song is playing
    */
    public void playSelectedSong(Songs selectedSong)
    { 
        switch(isPlaying)
        {
            case 1:
        mediaplayer.pause();
        isPlaying=0;
        break;
            case 0:
               File file = new File(selectedSong.getPath());
        filePath = file.toURI().toString();
        Media media = new Media(filePath);
        mediaplayer = new MediaPlayer(media);
        isPlaying=1;
        mediaplayer.play();
        
       // mediaplayer.setOnEndOfMedia(new Runnable() {
        //   @Override
        //   public void run() {
         //      System.out.println("Song over");
         //  }
     //  });
      }
    }
    /*
    method that plays song from the songsInPlaylist and also
    stops the mediaplayer if the song is playing
    */
    public void playSelectedSongInPlaylist(SongsInPlaylist songin)
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
/*
    method that clears list fo playlists and then fills it with
    playlists from the database
    */
    public void loadPlaylists() throws SQLException
    {
        allPlaylists.clear();
        allPlaylists.addAll(manager.getPlaylists());
    }
    /*
    method that clears list fo songs and then fills it with
    songs from the database
    */
    public void loadAllSongs() throws SQLException
    {
        allSongs.clear();
        allSongs.addAll(manager.getAllSongs());
    }
    /*
    returns the list of songs
    */
    public ObservableList<Songs> getAllSongs() throws SQLException
    {
        return allSongs;
   
    }
    /*
    returns the list of playlists
    */
    public ObservableList<Playlists> getPlaylists() throws SQLException
    {
        return allPlaylists;
    }
    /*
    adds song to the list of songs and to the database
    */
    public void addSong(Songs song)
{
    manager.addSong(song);
    allSongs.add(song);
    
}
    /*
    adds playlist to the list of playlists and to the database
    */
    public void addPlaylist(Playlists playlist)
{
    manager.addPlaylist(playlist);
    allPlaylists.add(playlist);
    
}
    /*
    Read the name of method!
    */
    public void closeWindow(Button button)
    {
       Stage stage = (Stage) button.getScene().getWindow();
       stage.close();
    }
    /*
    deletes selected song from the list of songs and from database
    */
    public void delete(Songs selectedSong)
    {
        manager.delete(selectedSong);
        allSongs.remove(selectedSong);
        
    }
    /*
    deletes playlists from list of playlists and form database
    */
    public void deletePlaylist(Playlists playlist)
    {
        manager.deletePlaylist(playlist);
        allPlaylists.remove(playlist);
        
    }
    /*
    deletes selected son in playlist from database and from list
    of songs in playlist
    */
    public void deleteSongsInPlaylist(SongsInPlaylist songInPlaylist)
    {
        manager.deleteSongsInPlaylist(songInPlaylist);
      songInP.remove(songInPlaylist);   
    }
    /*
     puts into the list of songs only the songs that contains
    query(part) in title or in artist and also returns this list of songs
    */
    public ObservableList<Songs> getSongsByQuery(String part)
    {
        allSongs.setAll(manager.getSongsByQuery(part));
        return allSongs;
    }
    /*
    puts into the list of songs in playlist only the songs that contains
    query(id) and then returns this list
    */
    public ObservableList<SongsInPlaylist> getSongsById(int id)
    {
        songInP.setAll(manager.getSongsById(id));
        return songInP;
    }
    /*
     method that edits song and "updates" list
    */
    public void edit(Songs song) throws SQLException
    {
        manager.edit(song);
        loadAllSongs();
    }
    /*
    method that edits playlist and "updates" list
    */
    public void editPlaylist(Playlists playlist) throws SQLException
    {
        manager.editPlaylist(playlist);
        loadPlaylists();
    }
    /*
    returns mediaplayer
    */
    public MediaPlayer getMediaPlayer()
    {
        return mediaplayer;
    }
    /*
    adds song in playlist to the database
    */
    public void addSongsToPlaylist(Songs song,Playlists playlist, int listOrder)
    { 
        manager.addSongsToPlaylist(song, playlist, listOrder);
        songInP.add(new SongsInPlaylist(song.getId(),playlist.getId(), listOrder));
    }
    /*
    i kow it is weird but this method takes all the songs and all the songs
    in playlist and compares them and then takes title, path and duration from
    song, gives it to the song in playlist that got the same id as song
    */
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
                   sdas.setDuration(song.getDuration());
                   test.add(sdas);
                   
               }
            }
        }
        
        return test;
    }
    /*
    takes the songs in playlist and sorts them in special order 
    */
    public ObservableList<SongsInPlaylist> songInOrder()
    {
        ObservableList<SongsInPlaylist> order = FXCollections.observableArrayList();
        for(int i=0;i<=test().size();i++)
        {
            for(SongsInPlaylist songinTest : test())
            {
                if(songinTest.getSortOrder()==i)
                {
                    order.add(songinTest);
                }
            }
        }
        return order;
    }
    /*
    this method updates songs in playlist
    */
     public void updateSongInPlayList(SongsInPlaylist songinPlay)
{
 manager.updateSongInPlayList(songinPlay);
 
}
}

