/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.bll;

import java.sql.SQLException;
import java.util.List;
import tunes.be.Playlists;
import tunes.be.Songs;
import tunes.be.SongsInPlaylist;
import tunes.dal.ConnectionManager;

/**
 *
 * @author Pepe15224
 */
public class BllManager {
  
    ConnectionManager cm = new ConnectionManager();
  
    public List<Songs> getAllSongs() throws SQLException
    {
      return cm.getAllSongs();
    }
    
    public void addSong(Songs song)
    {
    cm.addSong(song);
    }
    
    public void delete(Songs selectedSong)
    {
        cm.delete(selectedSong);
    }
    
    public List<Songs> getSongsByQuery(String part)
    {
       return cm.getSongsByQuery(part);
    }
    
    public void edit(Songs song)
    {
        cm.edit(song);
    }
    
    public List<Playlists> getPlaylists() throws SQLException
    {
        return cm.getPlaylists();
    }
    
    public void addPlaylist(Playlists playlist)
    {
        cm.addPlaylist(playlist);
    }
    
    public void editPlaylist(Playlists playlist)
    {
        cm.editPlaylist(playlist);
    }
    
    public void deletePlaylist(Playlists playlist)
    {
        cm.deletePlaylist(playlist);
    }
    
    public void addSongsToPlaylist(Songs song,Playlists playlist, int listOrder)
    {
        cm.addSongsToPlaylist(song, playlist, listOrder);
    }
    
   public List<SongsInPlaylist> getSongsById(int id)
   {
       return cm.getSongsById(id);
   }
   
   public void deleteSongsInPlaylist(SongsInPlaylist songInPlaylist)
   {
       cm.deleteSongsInPlaylist(songInPlaylist);
   }
   
    public void updateSongInPlayList(SongsInPlaylist songinPlay)
    {
        cm.updateSongInPlayList(songinPlay);
    }
}
