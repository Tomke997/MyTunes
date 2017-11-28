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
             Songs s = new Songs();
             s.setId(rs.getInt("id"));
             s.setTitle(rs.getString("title"));
             s.setArtist(rs.getString("artist"));
             s.setCategory(rs.getString("category"));
             s.setDuration(rs.getString("duration"));
             s.setPath(rs.getString("path"));
             
             allSongs.add(s); 
            }
         }
         catch (SQLException ex) {
            Logger.getLogger(ConnectionManager.class.getName()).log(
                    Level.SEVERE, null, ex);
          }
        return allSongs;
}
}