/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tunes.be;

/**
 *
 * @author Pepe15224
 */
public class SongsInPlaylist {
    
    private int info;
    private int PlayList;
    private int sortOrder;
    private String title;
    private String path;
    private Double duration;

    public Double getDuration() {
        return duration;
    }

    public void setDuration(Double duration) {
        this.duration = duration;
    }

    public int getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(int sortOrder) {
        this.sortOrder = sortOrder;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPlayList() {
        return PlayList;
    }

    public void setPlayList(int PlayList) {
        this.PlayList = PlayList;
    }

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public SongsInPlaylist(int info, int PlayList, int sortOrder) {
        this.info = info;
        this.PlayList = PlayList;
        this.sortOrder = sortOrder;
    }
    
    @Override
    public String toString() {
        return title;
    }   
}
