package com.crio.jukebox.services;

import java.util.Optional;

import com.crio.jukebox.entities.Song;

public interface ISongService {
    public String loadData(String fileName);
    public Optional<Song> getSong(String id);
}