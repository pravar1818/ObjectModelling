package com.crio.jukebox.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.crio.jukebox.entities.Song;
import com.crio.jukebox.repositories.ISongRepository;


public class SongService implements ISongService {

    ISongRepository songRepository;

    public SongService(ISongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public String loadData(String fileName) {
        // TODO Auto-generated method stub
        Path pathToFile = Paths.get(fileName);
        try (BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.US_ASCII)) {
            String line = br.readLine();
            while (line != null) {

                String[] attributes = line.split(",");

                String name = attributes[0];
                String genre = attributes[1];
                String albumName = attributes[2];
                String mainArtist = attributes[3];
                String otherArtist = attributes[4];

                String[] otherArtistArray = otherArtist.split("#");
                List<String> otherArtistList = new ArrayList<>();

                for (int i = 0; i < otherArtistArray.length; i++) {
                    otherArtistList.add(otherArtistArray[i]);
                }
                Song song = new Song(name, genre, albumName, mainArtist, otherArtistList);
                Song returnedSong = songRepository.save(song);
                line = br.readLine();
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return "Songs Loaded successfully";

    }

    @Override
    public Optional<Song> getSong(String id) {
        // TODO Auto-generated method stub
        return songRepository.findById(id);
    }

   
    
}