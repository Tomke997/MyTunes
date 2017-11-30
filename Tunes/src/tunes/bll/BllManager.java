/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.bll;

import java.sql.SQLException;
import java.util.List;
import tunes.be.Songs;
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
}
