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
import tunes.be.Songs;

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
                     rs.getString("duration"),
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
            pstmt.setString(4, song.getDuration());
            pstmt.setString(5, song.getPath());

            int affected = pstmt.executeUpdate();
            if (affected<1)
                throw new SQLException("Prisoner could not be added");

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
    
    public void remove(Songs selectedSong) {
        try (Connection con = cc.getConnection()) {
            String sql
                    = "DELETE FROM info WHERE id=?";
            PreparedStatement pstmt
                    = con.prepareStatement(sql);
            pstmt.setInt(1, selectedSong.getId());
            pstmt.execute();
        }
        catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
        }
    }
}