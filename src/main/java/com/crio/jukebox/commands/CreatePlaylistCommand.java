package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;

import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.services.IPlaylistService;


public class CreatePlaylistCommand implements ICommand {

    IPlaylistService playlistService;
    
    public CreatePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub
        int n = tokens.size();
        List<String> listOfSongId = new ArrayList<>(); 
        for(int i=3;i<n;i++)
            listOfSongId.add(tokens.get(i));

        String id = "";
        try{
           id =  playlistService.create(tokens.get(1), tokens.get(2), listOfSongId);
        }catch(SongNotFoundException se){
            System.out.println("Some Requested Songs Not Available. Please try again.");
        }catch(UserNotFoundException ue){
            System.out.println("Requested User Not Found. Please try again.");
        }
        System.out.println("Playlist ID - " + id);
    }
    
}