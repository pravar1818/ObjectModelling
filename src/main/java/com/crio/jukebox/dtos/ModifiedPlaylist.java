package com.crio.jukebox.dtos;

import java.util.List;

public class ModifiedPlaylist {
    private String playlistId;
    private String playlistName;
    private List<String> songId;

    public ModifiedPlaylist() {
    }

    public ModifiedPlaylist(String playlistId, String playlistName, List<String> songId) {
        this.playlistId = playlistId;
        this.playlistName = playlistName;
        this.songId = songId;
    }
    
    public List<String> getSongId() {
        return songId;
    }

    public String getPlaylistId() {
        return playlistId;
    }

    public void setPlaylistId(String playlistId) {
        this.playlistId = playlistId;
    }

    public String getPlaylistName() {
        return playlistName;
    }

    public void setPlaylistName(String playlistName) {
        this.playlistName = playlistName;
    }

    public void setSongId(List<String> songId) {
        this.songId = songId;
    }
    
}