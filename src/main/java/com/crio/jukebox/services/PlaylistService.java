package com.crio.jukebox.services;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.crio.jukebox.dtos.CurrentPlayingSong;
import com.crio.jukebox.dtos.ModifiedPlaylist;
import com.crio.jukebox.entities.Playlist;
import com.crio.jukebox.entities.Song;
import com.crio.jukebox.entities.User;
import com.crio.jukebox.exceptions.PlaylistNotFoundException;
import com.crio.jukebox.exceptions.SongNotFoundException;
import com.crio.jukebox.exceptions.UserNotFoundException;
import com.crio.jukebox.repositories.IPlaylistRepository;
import com.crio.jukebox.repositories.ISongRepository;
import com.crio.jukebox.repositories.IUserRepository;


public class PlaylistService implements IPlaylistService {

    IPlaylistRepository playlistRepository;
    IUserRepository userRepository;
    ISongRepository songRepository;

    public PlaylistService(IPlaylistRepository playlistRepository, IUserRepository userRepository,
            ISongRepository songRepository) {
        this.playlistRepository = playlistRepository;
        this.userRepository = userRepository;
        this.songRepository = songRepository;
    }

    @Override
    public String create(String userId, String name, List<String> songId) throws UserNotFoundException, SongNotFoundException{
        // TODO Auto-generated method stub
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot create playList. User for given id:" + userId + " not found!"));
        List<Song> songList = new ArrayList<>();

        for(String id : songId){
            Song song = songRepository.findById(id).orElseThrow(()-> new SongNotFoundException("Cannot find song. Song id- "+songId+" is not present!"));
            songList.add(song);
        }

        Playlist playlist = new Playlist(name,owner,songList,songId.get(0));
        Playlist resultantPlaylist = playlistRepository.save(playlist);

        List<Playlist> oldPlaylist = owner.getPlaylist();
        if(oldPlaylist==null){
            List<Playlist> newPlaylist = new ArrayList<>();
            newPlaylist.add(resultantPlaylist);
            owner.setPlaylist(newPlaylist);
        }else{
            oldPlaylist.add(resultantPlaylist);
            owner.setPlaylist(oldPlaylist);
        }    

        return resultantPlaylist.getId();
    }

    @Override
    public String delete(String userId, String id) {
        // TODO Auto-generated method stub
        Playlist playlist = playlistRepository.findById(id).orElseThrow(()-> new PlaylistNotFoundException("Cannot find playlist, playlist id- "+id+" is not present!"));
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot create playList. User for given id:" + userId + " not found!"));

        List<Playlist> oldPlaylist = owner.getPlaylist();
        oldPlaylist.remove(playlist);
        owner.setPlaylist(oldPlaylist);
        
        playlistRepository.deleteById(id);

        return "Delete Successful";
    }

    @Override
    public ModifiedPlaylist addSong(String userId, String id, List<String> songId) {
        // TODO Auto-generated method stub
        Playlist playlist = playlistRepository.findById(id).orElseThrow(()-> new PlaylistNotFoundException("Cannot find playlist, playlist id- "+id+" is not present!"));
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot create playList. User for given id:" + userId + " not found!"));

        List<Song> songList = playlist.getSongList();

        for(String sId : songId){
            Song songFound = songRepository.findById(sId).orElseThrow(()-> new SongNotFoundException("Cannot find song. Song id- "+songId+" is not present!"));
            songList.add(songFound);
        }

        songList = songList.stream().distinct().collect(Collectors.toList());

        playlist.setSongList(songList);

        playlistRepository.deleteById(id);
        playlist = playlistRepository.save(playlist);

        List<Playlist> newPlaylist = new ArrayList<>();
        List<Playlist> listOfPlaylist = owner.getPlaylist();
        
        for(Playlist playlist2 : listOfPlaylist){
            if(playlist2.getId().equals(id))
                continue;
            newPlaylist.add(playlist2);
        }

        newPlaylist.add(playlist);
        owner.setPlaylist(newPlaylist);
        userRepository.deleteById(userId);
        userRepository.save(owner);

        List<Song> finalSongList = playlist.getSongList();
        List<String> finalSongId = new ArrayList<>();

        for(Song sg : finalSongList){
            finalSongId.add(sg.getId());
        }

        return new ModifiedPlaylist(id,playlist.getName(),finalSongId);
    }

    @Override
    public ModifiedPlaylist deleteSong(String userId, String id, List<String> songId) {
        // TODO Auto-generated method stub
        Playlist playlist = playlistRepository.findById(id).orElseThrow(()-> new PlaylistNotFoundException("Cannot find playlist, playlist id- "+id+" is not present!"));
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot create playList. User for given id:" + userId + " not found!"));

        List<Song> songList = playlist.getSongList();

        for(String sId : songId){
            for(Song song : songList){
                if(song.getId().equals(sId)){
                    break;
                }
            }
            Song songFound = songRepository.findById(sId).orElseThrow(()-> new SongNotFoundException("Cannot find song. Song id- "+songId+" is not present!"));
            songList.remove(songFound);
        }

        playlistRepository.deleteById(id);
        playlist = playlistRepository.save(playlist);

        List<Playlist> newPlaylist = new ArrayList<>();
        List<Playlist> listOfPlaylist = owner.getPlaylist();
        
        for(Playlist playlist2 : listOfPlaylist){
            if(playlist2.getId().equals(id))
                continue;
            newPlaylist.add(playlist2);
        }

        newPlaylist.add(playlist);
        owner.setPlaylist(newPlaylist);
        userRepository.deleteById(userId);
        userRepository.save(owner);

        List<Song> finalSongList = playlist.getSongList();
        List<String> finalSongId = new ArrayList<>();

        for(Song sg : finalSongList){
            finalSongId.add(sg.getId());
        }

        return new ModifiedPlaylist(id,playlist.getName(),finalSongId);
    }

    @Override
    public CurrentPlayingSong playPlaylist(String userId, String id) {
        // TODO Auto-generated method stub
        Playlist playlist = playlistRepository.findById(id).orElseThrow(()-> new PlaylistNotFoundException("Cannot find playlist, playlist id- "+id+" is not present!"));
        User owner = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot create playList. User for given id:" + userId + " not found!"));
        

        String currentSongId = playlist.getCurrentSongId();

        Song song = songRepository.findById(currentSongId).orElseThrow(()-> new SongNotFoundException("Cannot find song. Song id- "+currentSongId+" is not present!"));
        
        owner.setActivePlaylist(playlist);

        userRepository.deleteById(userId);
        User newUser = userRepository.save(owner);

        return new CurrentPlayingSong(song.getName(), song.getAlbum(), song.getOtherArtist());
    }

    @Override
    public CurrentPlayingSong playSpecifiSong(String userId, String songId) {
        // TODO Auto-generated method stub

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot create playList. User for given id:" + userId + " not found!"));
       
        Playlist activePlaylist = user.getActivePlaylist();
        List<Song> listOfSongs = activePlaylist.getSongList();

        Song song = null;
        for(Song sn : listOfSongs){
            if(sn.getId().equals(songId)){
                song = sn;
                break;
            }
        }
        
        if(song==null)
            throw new SongNotFoundException("Cannot find song. Song id- "+songId+" is not present!");

       
        activePlaylist.setCurrentSongId(songId);
        playlistRepository.deleteById(activePlaylist.getId());
        Playlist newPlaylist = playlistRepository.save(activePlaylist);

        List<Playlist> listOfPlaylists = user.getPlaylist();
        int i = 0;
        for(Playlist playlist : listOfPlaylists){
            if(playlist.getId().equals(activePlaylist.getId())){
                listOfPlaylists.remove(i);
                break;
            }
            i++;
        }
        listOfPlaylists.add(activePlaylist);
        user.setPlaylist(listOfPlaylists);
        userRepository.deleteById(userId);
        User newUser = userRepository.save(user);
        return new CurrentPlayingSong(song.getName(), song.getAlbum(), song.getOtherArtist());
        
    }

    @Override
    public CurrentPlayingSong playNextSong(String userId, String button) {
        // TODO Auto-generated method stub

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot create playList. User for given id:" + userId + " not found!"));
        Playlist activePlaylist = user.getActivePlaylist();
        String activeSongId = activePlaylist.getCurrentSongId();
    
        
        int n = activePlaylist.getSongList().size();
        int i=0;
        int currentIndex = -1;
        for(Song sn : activePlaylist.getSongList()){
            if(sn.getId().equals(activeSongId)){
                currentIndex = i;
                break;
            }
            i++;
        }

        int nextIndex = (currentIndex+1)%n;
        Song requiredSong = activePlaylist.getSongList().get(nextIndex);

        //Changing the currentSongId in Playlist.java
        activePlaylist.setCurrentSongId(requiredSong.getId());
        playlistRepository.deleteById(activePlaylist.getId());
        Playlist newPlaylist = playlistRepository.save(activePlaylist);

        List<Playlist> listOfPlaylists = user.getPlaylist();
        int it = 0;
        for(Playlist playlist : listOfPlaylists){
            if(playlist.getId().equals(activePlaylist.getId())){
                listOfPlaylists.remove(it);
                break;
            }
            it++;
        }
        listOfPlaylists.add(activePlaylist);
        user.setPlaylist(listOfPlaylists);
        userRepository.deleteById(userId);
        User newUser = userRepository.save(user);
        return new CurrentPlayingSong(requiredSong.getName(), requiredSong.getAlbum(), requiredSong.getOtherArtist());
    }

    @Override
    public CurrentPlayingSong playPreviousSong(String userId, String button) {
        // TODO Auto-generated method stub
        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("Cannot create playList. User for given id:" + userId + " not found!"));
        Playlist activePlaylist = user.getActivePlaylist();
        String activeSongId = activePlaylist.getCurrentSongId();
    
        
        int n = activePlaylist.getSongList().size();
        int i=0;
        int currentIndex = -1;
        for(Song sn : activePlaylist.getSongList()){
            if(sn.getId().equals(activeSongId)){
                currentIndex = i;
                break;
            }
            i++;
        }

        int nextIndex;

        if(currentIndex==0)
            nextIndex = n-1;
        else
            nextIndex = (currentIndex - 1)%n;
    

        Song requiredSong = activePlaylist.getSongList().get(nextIndex);

        //Changing the currentSongId in Playlist.java
        activePlaylist.setCurrentSongId(requiredSong.getId());
        playlistRepository.deleteById(activePlaylist.getId());
        Playlist newPlaylist = playlistRepository.save(activePlaylist);

        List<Playlist> listOfPlaylists = user.getPlaylist();
        int it = 0;
        for(Playlist playlist : listOfPlaylists){
            if(playlist.getId().equals(activePlaylist.getId())){
                listOfPlaylists.remove(it);
                break;
            }
            it++;
        }
        listOfPlaylists.add(activePlaylist);
        user.setPlaylist(listOfPlaylists);
        userRepository.deleteById(userId);
        User newUser = userRepository.save(user);
        return new CurrentPlayingSong(requiredSong.getName(), requiredSong.getAlbum(), requiredSong.getOtherArtist());
    }
    
}