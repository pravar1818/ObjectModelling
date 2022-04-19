package com.crio.jukebox.commands;

import java.util.List;

import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.services.IPlaylistService;


public class DeletePlaylistCommand implements ICommand {

    IPlaylistService playlistService;

    public DeletePlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub
        String msg = "";
        try{
            msg = playlistService.delete(tokens.get(1), tokens.get(2));
        }catch(PlaylistNotFoundException pe){
            System.out.println("Playlist Not Found");
        }catch(UserNotFoundException ue){
            System.out.println("User Not Found");
        }
        System.out.println(msg);
    }
    
}