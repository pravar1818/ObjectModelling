package com.crio.jukebox.services;

import java.util.List;

import com.crio.jukebox.dtos.CurrentPlayingSong;
import com.crio.jukebox.dtos.ModifiedPlaylist;

public interface IPlaylistService {
    public String create(String userId, String name, List<String>songId);
    public String delete(String userId, String id);
    public ModifiedPlaylist addSong(String userId, String id, List<String>songId);
    public ModifiedPlaylist deleteSong(String userId, String id, List<String>songId);
    public CurrentPlayingSong playPlaylist(String userId, String id);
    public CurrentPlayingSong playSpecifiSong(String userId, String songId);
    public CurrentPlayingSong playNextSong(String userId, String button);
    public CurrentPlayingSong playPreviousSong(String userId, String button);
}