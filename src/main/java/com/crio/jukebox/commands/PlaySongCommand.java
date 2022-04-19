package com.crio.jukebox.commands;

import java.util.ArrayList;
import java.util.List;

import com.crio.codingame.exceptions.UserNotFoundException;
import com.crio.jukebox.dtos.CurrentPlayingSong;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.services.IPlaylistService;


public class PlaySongCommand implements ICommand {

    IPlaylistService playlistService;

    public PlaySongCommand(IPlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @Override
    public void execute(List<String> tokens) {
        // TODO Auto-generated method stub

        CurrentPlayingSong currentPlayingSong = new CurrentPlayingSong();

        if(tokens.get(2).equals("BACK")){
            try{
                currentPlayingSong = playlistService.playPreviousSong(tokens.get(1), tokens.get(2));
                System.out.println("Current Song Playing");
                System.out.println("Song - " + currentPlayingSong.getSongName());
                System.out.println("Album - " + currentPlayingSong.getAlbum());
                List<String> listOfArtist = new ArrayList<>();
                listOfArtist = currentPlayingSong.getArtists();
                if(listOfArtist!=null){
                    System.out.print("Artists - ");
                    int n = listOfArtist.size();
                    for(int i=0;i<n-1;i++){
                        System.out.print(listOfArtist.get(i)+",");
                    }
                    System.out.println(listOfArtist.get(n-1));
                }
            }catch(UserNotFoundException ue){
                System.out.println("User Not Found.");
            }catch(SongNotFoundException se){
                System.out.println("Song Not Found in the current active playlist.");
            }
        }else if(tokens.get(2).equals("NEXT")){
            try{
                currentPlayingSong = playlistService.playNextSong(tokens.get(1), tokens.get(2));
                System.out.println("Current Song Playing");
                System.out.println("Song - " + currentPlayingSong.getSongName());
                System.out.println("Album - " + currentPlayingSong.getAlbum());
                List<String> listOfArtist = new ArrayList<>();
                listOfArtist = currentPlayingSong.getArtists();
                if(listOfArtist!=null){
                    System.out.print("Artists - ");
                    int n = listOfArtist.size();
                    for(int i=0;i<n-1;i++){
                        System.out.print(listOfArtist.get(i)+",");
                    }
                    System.out.println(listOfArtist.get(n-1));
                }
            }catch(UserNotFoundException ue){
                System.out.println("User Not Found.");
            }catch(SongNotFoundException se){
                System.out.println("Song Not Found in the current active playlist.");
            }
            
        }else{
            try{
                currentPlayingSong = playlistService.playSpecifiSong(tokens.get(1), tokens.get(2));
                System.out.println("Current Song Playing");
                System.out.println("Song - " + currentPlayingSong.getSongName());
                System.out.println("Album - " + currentPlayingSong.getAlbum());
                List<String> listOfArtist = new ArrayList<>();
                listOfArtist = currentPlayingSong.getArtists();
                if(listOfArtist!=null){
                    System.out.print("Artists - ");
                    int n = listOfArtist.size();
                    for(int i=0;i<n-1;i++){
                        System.out.print(listOfArtist.get(i)+",");
                    }
                    System.out.println(listOfArtist.get(n-1));
                }
            }catch(UserNotFoundException ue){
                System.out.println("User Not Found.");
            }catch(SongNotFoundException se){
                System.out.println("Given song id is not a part of the active playlist");
            }
        }
        
        
    }
    
}