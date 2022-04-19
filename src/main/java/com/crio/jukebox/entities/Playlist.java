package com.crio.jukebox.entities;

import java.util.List;

public class Playlist {
    private String id;
    private String name;
    private User owner;
    private List<Song> songList;
    private String currentSongId;
    

    public Playlist() {
    }

    public Playlist(String name, User owner, List<Song> songList, String currentSongId) {
        this.name = name;
        this.owner = owner;
        this.songList = songList;
        this.currentSongId = currentSongId;
        
    }

    public Playlist(String id, String name, User owner, List<Song> songList, String currentSongId) {
        this.id = id;
        this.name = name;
        this.owner = owner;
        this.songList = songList;
        this.currentSongId = currentSongId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Song> getSongList() {
        return songList;
    }

    public void setSongList(List<Song> songList) {
        this.songList = songList;
    }

    public String getCurrentSongId() {
        return currentSongId;
    }

    public void setCurrentSongId(String currentSongId) {
        this.currentSongId = currentSongId;
    }
    
}