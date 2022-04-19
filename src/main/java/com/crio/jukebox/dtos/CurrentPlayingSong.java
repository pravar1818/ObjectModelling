package com.crio.jukebox.dtos;

import java.util.List;

public class CurrentPlayingSong {
    private String songName;
    private String album;
    private List<String> artists;

    public CurrentPlayingSong() {
    }

    public CurrentPlayingSong(String songName, String album, List<String> artists) {
        this.songName = songName;
        this.album = album;
        this.artists = artists;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public List<String> getArtists() {
        return artists;
    }
    
    public void setArtists(List<String> artists) {
        this.artists = artists;
    }
    
}