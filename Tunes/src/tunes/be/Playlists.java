/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.be;

import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Pepe15224
 */
public class Playlists {
    
    private int id;
    private String name;
    private final ListProperty<SongsInPlaylist> songsInPlaylist = new SimpleListProperty<>();

    public Playlists() {
        songsInPlaylist.set(FXCollections.observableArrayList());
    }

    public ObservableList getSongsInPlaylist() {
        return songsInPlaylist.get();
    }

    public void setSongsInPlaylist(ObservableList value) {
        songsInPlaylist.set(value);
    }

    public ListProperty songsInPlaylistProperty() {
        return songsInPlaylist;
    }
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private Float time;

    public Float getTime() {
        return time;
    }

    public void setTime(Float time) {
        this.time = time;
    }

    private int songs;

    public int getSongs() {
        return songs;
    }

    public void setSongs(int songs) {
        this.songs = songs;
    }

    public Playlists(int id, String name, Float time, int songs) {
        this.id = id;
        this.name = name;
        this.time = time;
        this.songs = songs;
    }
}
