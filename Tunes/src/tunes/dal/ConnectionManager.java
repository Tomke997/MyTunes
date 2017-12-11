/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.dal;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import tunes.be.Playlists;
import tunes.be.Songs;
import tunes.be.SongsInPlaylist;

/**
 *
 * @author Pepe15224
 */
public class ConnectionManager {
    private ConnectionController cc = new ConnectionController();
    
    public List<Songs> getAllSongs() throws SQLServerException, SQLException
    {
        List<Songs> allSongs = new ArrayList();
        try (Connection con = cc.getConnection())
        {
         PreparedStatement pstmt = con.prepareCall("SELECT * FROM info");
         ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
             Songs s = new Songs(rs.getInt("id"),
                     rs.getString("title"),
                     rs.getString("artist"),
                     rs.getString("category"),
                     rs.getFloat("duration"),
                     rs.getString("path"));
             
             allSongs.add(s); 
            }
         }
         catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
          }
        return allSongs;
}
    public void addSong(Songs song) {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "INSERT INTO info"
                    + "(title, artist, category, duration, path) "
                    + "VALUES(?,?,?,?,?)";
            PreparedStatement pstmt
                    = con.prepareStatement(
                            sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getArtist());
            pstmt.setString(3, song.getCategory());
            pstmt.setFloat(4, song.getDuration());
            pstmt.setString(5, song.getPath());

            int affected = pstmt.executeUpdate();
            if (affected<1)
                throw new SQLException("Song could not be added");

            // Get database generated id
            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                song.setId(rs.getInt(1));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    
    public void delete(Songs selectedSong) {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "DELETE FROM info WHERE id=?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setInt(1, selectedSong.getId());
            pstmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public List<Songs> getSongsByQuery(String part)
        {

        List<Songs> songsByQuery = new ArrayList();

        try (Connection con = cc.getConnection()) 
        {
          String query
                    = "SELECT * FROM info "
                    + "WHERE title LIKE ? OR artist LIKE ?";
             
            PreparedStatement pstmt
                    = con.prepareStatement(query);
            pstmt.setString(1, "%" + part+ "%");
            pstmt.setString(2,  "%" + part + "%");

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                Songs s = new Songs(rs.getInt("id"),
                     rs.getString("title"),
                     rs.getString("artist"),
                     rs.getString("category"),
                     rs.getFloat("duration"),
                     rs.getString("path"));
                
                songsByQuery.add(s);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songsByQuery;
    }
    public void edit(Songs song) {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "UPDATE info SET "
                    + "title=?, artist=?, category=?, duration=?, path=? "
                    + "WHERE id=?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setString(1, song.getTitle());
            pstmt.setString(2, song.getArtist());
            pstmt.setString(3, song.getCategory());
            pstmt.setFloat(4, song.getDuration());
            pstmt.setString(5, song.getPath());
            pstmt.setInt(6, song.getId());

            int affected = pstmt.executeUpdate();
            if (affected<1)
                throw new SQLException("Song could not be edited");

        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    public List<Playlists> getPlaylists() throws SQLServerException, SQLException
    {
        List<Playlists> allPlaylists = new ArrayList();
        try (Connection con = cc.getConnection())
        {
         PreparedStatement pstmt = con.prepareCall("SELECT * FROM PlayList");
         ResultSet rs = pstmt.executeQuery();
            while(rs.next())
            {
             Playlists p = new Playlists(rs.getInt("id"), rs.getString("name"),rs.getFloat("time"),rs.getInt("songs"));
             
             allPlaylists.add(p); 
            }
         }
         catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
          }
        return allPlaylists;
}
    public void addPlaylist(Playlists playlist) {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "INSERT INTO PlayList "
                    + "(name, songs, time) "
                    + "VALUES(?,?,?)";
            PreparedStatement pstmt
                    = con.prepareStatement(
                            sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, playlist.getName());
            pstmt.setInt(2, playlist.getSongs());
            pstmt.setFloat(3, playlist.getTime());
            

            int affected = pstmt.executeUpdate();
            if (affected<1)
                throw new SQLException("Playlist could not be added");

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                playlist.setId(rs.getInt(1));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    public void editPlaylist(Playlists playlist) {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "UPDATE PlayList SET "
                    + "name=?, songs=?, time=? "
                    + "WHERE id=?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setString(1, playlist.getName());
            pstmt.setInt(2, playlist.getSongs());
            pstmt.setFloat(3, playlist.getTime());
            pstmt.setInt(4, playlist.getId());

            int affected = pstmt.executeUpdate();
            if (affected<1)
                throw new SQLException("Playlist could not be edited");

        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
        
        }
    }
    public void deletePlaylist(Playlists playlist)
    {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "DELETE FROM PlayList WHERE id=? "+
                    "DELETE FROM SongsInPlaylists WHERE PlayList=?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setInt(1, playlist.getId());
            pstmt.setInt(2, playlist.getId());
            pstmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void addSongsToPlaylist(Songs song,Playlists playlist, int listOrder)
    {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "INSERT INTO SongsInPlaylists "
                    + "(info, PlayList, sortOrder) "
                    + "VALUES(?,?,?)";
            PreparedStatement pstmt
                    = con.prepareStatement(
                            sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, song.getId());
            pstmt.setInt(2, playlist.getId());
            pstmt.setInt(3, listOrder);
            int affected = pstmt.executeUpdate();
            if (affected<1)
                throw new SQLException("Song could not be added to Playlist");

            ResultSet rs = pstmt.getGeneratedKeys();
            if (rs.next()) {
                playlist.setId(rs.getInt(1));
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
    public List<SongsInPlaylist> getSongsById(int id)
    {
        List<SongsInPlaylist> songsById = new ArrayList();

        try (Connection con = cc.getConnection()) 
        {
          String query
                    = "SELECT * FROM SongsInPlaylists "
                    + "WHERE PlayList LIKE ?";
             
            PreparedStatement pstmt
                    = con.prepareStatement(query);
            pstmt.setInt(1,id);
           ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                SongsInPlaylist s = new SongsInPlaylist(rs.getInt("info"),rs.getInt("PlayList"),rs.getInt("sortOrder"));
                
                songsById.add(s);
            }
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return songsById;
    }
    public void deleteSongsInPlaylist(SongsInPlaylist songInPlaylist)
    {
       try (Connection con = cc.getConnection()) {
            String sql
                    = "DELETE FROM SongsInPlaylists WHERE info=? AND PlayList =?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setInt(1, songInPlaylist.getInfo());
            pstmt.setInt(2, songInPlaylist.getPlayList());
            pstmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void updateSongInPlayList(SongsInPlaylist songinPlay)
    {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "UPDATE SongsInPlaylists SET "
                    + "PlayList=?, sortOrder=? "
                    + "WHERE info=?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setInt(1, songinPlay.getPlayList());
            pstmt.setInt(2, songinPlay.getSortOrder());
            pstmt.setInt(3, songinPlay.getInfo());
            int affected = pstmt.executeUpdate();     
            if (affected<1)
                throw new SQLException("Songs In Playlist could not be updated");

        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
        
        }
    }
}
