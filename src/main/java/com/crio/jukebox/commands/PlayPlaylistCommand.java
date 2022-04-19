package com.crio.jukebox.commands;

import java.util.List;

import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.dtos.CurrentPlayingSong;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.services.IPlaylistService;


public class PlayPlaylistCommand implements ICommand {

    IPlaylistService playlistService;

    public PlayPlaylistCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub

        CurrentPlayingSong currentPlayingSong = new CurrentPlayingSong();
        try{
            currentPlayingSong = playlistService.playPlaylist(tokens.get(1), tokens.get(2));
        }catch(PlaylistNotFoundException pe){
            System.out.println("Playlist Not Found");
        }catch(UserNotFoundException ue){
            System.out.println("User Not Found");
        }catch(SongNotFoundException se){
            System.out.println("Playlist is empty.");
        }
        System.out.println("Current Song Playing");
        System.out.println("Song - " + currentPlayingSong.getSongName());
        System.out.println("Album - " + currentPlayingSong.getAlbum());
        List<String> listOfArtist = currentPlayingSong.getArtists();
        System.out.print("Artists - ");
        int n = listOfArtist.size();
        for(int i=0;i<n-1;i++){
            System.out.print(listOfArtist.get(i)+",");
        }
        System.out.println(listOfArtist.get(n-1));
    }

    
    
}