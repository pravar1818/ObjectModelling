package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;

import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.dtos.ModifiedPlaylist;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.services.IPlaylistService;


public class ModifyPlaylistCommand implements ICommand {

    IPlaylistService playlistService;

    public ModifyPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub
        
        ModifiedPlaylist modifiedPlaylist = new ModifiedPlaylist();
        int sz = tokens.size();

        List<String> listOfSongId = new ArrayList<>(); 
        for(int i=4;i<sz;i++)
            listOfSongId.add(tokens.get(i));

        if(tokens.get(1).equals("ADD-SONG")){
            try{
                modifiedPlaylist = playlistService.addSong(tokens.get(2), tokens.get(3), listOfSongId);
            }catch(PlaylistNotFoundException pe){
                System.out.println("Playlist Not Found");
            }catch(UserNotFoundException ue){
                System.out.println("User Not Found");
            }catch(SongNotFoundException se){
                System.out.println("Some Requested Songs Not Available. Please try again.");
            }
        }else if(tokens.get(1).equals("DELETE-SONG")){
            try{
                modifiedPlaylist = playlistService.deleteSong(tokens.get(2), tokens.get(3), listOfSongId);
            }catch(PlaylistNotFoundException pe){
                System.out.println("Playlist Not Found");
            }catch(UserNotFoundException ue){
                System.out.println("User Not Found");
            }catch(SongNotFoundException se){
                System.out.println("Some Requested Songs for Deletion are not present in the playlist. Please try again.");
            }   
        }
        
        System.out.println("Playlist ID - " + modifiedPlaylist.getPlaylistId());
        System.out.println("Playlist Name - " + modifiedPlaylist.getPlaylistName());
        List<String> listOfNewSongId = modifiedPlaylist.getSongId();
        System.out.print("Song IDs - ");
        int n = listOfNewSongId.size();
        for(int i=0;i<n-1;i++){
            System.out.print(listOfNewSongId.get(i)+" ");
        }
        System.out.println(listOfNewSongId.get(n-1));
    }
    
}