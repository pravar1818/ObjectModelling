package com.crio.jukebox.repositories;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.crio.jukebox.entities.Song;


public class SongRepository implements ISongRepository {

    private final Map<String, Song> songMap;
    private Integer autoIncrement = 0;

    public SongRepository(){
        this.songMap = new LinkedHashMap<>();
    }

    public SongRepository(Map<String, Song> songMap) {
        this.songMap = songMap;
    }

    public SongRepository(Map<String, Song> songMap, Integer autoIncrement) {
        this.songMap = songMap;
        this.autoIncrement = autoIncrement;
    }


  
	@Override
    public Song save(Song entity) {
        // TODO Auto-generated method stub
        if(entity.getId() == null){
            autoIncrement++;
            Song song = new Song(Integer.toString(autoIncrement),entity.getName(),entity.getGenre(),entity.getAlbum(),entity.getMainArtist(),entity.getOtherArtist());
            songMap.put(song.getId(),song);
            return song;
        }
        songMap.put(entity.getId(),entity);
        return entity;
    }

    @Override
    public List<Song> findAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Song> findById(String id) {
        // TODO Auto-generated method stub
        return Optional.ofNullable(songMap.get(id));
    }

    @Override
    public boolean existsById(String id) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public void delete(Song entity) {
        // TODO Auto-generated method stub

    }

    @Override
    public void deleteById(String id) {
        // TODO Auto-generated method stub

    }

    @Override
    public long count() {
        // TODO Auto-generated method stub
        return 0;
    }

   
}