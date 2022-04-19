package com.crio.jukebox.entities;

import java.util.List;

public class Song {
    private String id;
    private String name;
    private String genre;
    private String album;
    private String mainArtist;
    private List<String> otherArtist;

    public Song() {
    }

    public Song(String name, String genre, String album, String mainArtist, List<String> otherArtist) {
        this.name = name;
        this.genre = genre;
        this.album = album;
        this.mainArtist = mainArtist;
        this.otherArtist = otherArtist;
    }

    public Song(String id, String name, String genre, String album, String mainArtist, List<String> otherArtist) {
        this.id = id;
        this.name = name;
        this.genre = genre;
        this.album = album;
        this.mainArtist = mainArtist;
        this.otherArtist = otherArtist;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getMainArtist() {
        return mainArtist;
    }

    public void setMainArtist(String mainArtist) {
        this.mainArtist = mainArtist;
    }

    public List<String> getOtherArtist() {
        return otherArtist;
    }

    public void setOtherArtist(List<String> otherArtist) {
        this.otherArtist = otherArtist;
    }

    

}