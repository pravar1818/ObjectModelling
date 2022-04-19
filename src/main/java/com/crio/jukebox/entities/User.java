package com.crio.jukebox.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String id;
    private String name;
    private List<Playlist> playlist;
    private Playlist activePlaylist;

    public User(){
        id = "";
        name = "";
        playlist = new ArrayList<>();
        activePlaylist = null;
    }

    public User(String id, String name, List<Playlist> playlist, Playlist activePlaylist) {
        this.id = id;
        this.name = name;
        this.playlist = playlist;
        this.activePlaylist = activePlaylist;
    }

    public User(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public List<Playlist> getPlaylist() {
        return playlist;
    }

    public void setPlaylist(List<Playlist> playlist) {
        this.playlist = playlist;
    }

    public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setId(String id) {
        this.id = id;
    }

    public Playlist getActivePlaylist() {
        return activePlaylist;
    }

    public void setActivePlaylist(Playlist activePlaylist) {
        this.activePlaylist = activePlaylist;
    }

    
}