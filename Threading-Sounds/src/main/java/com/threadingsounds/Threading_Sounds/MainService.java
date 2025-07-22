package com.threadingsounds.Threading_Sounds;

import java.util.List;

import org.springframework.stereotype.Service;

import com.threadingsounds.Threading_Sounds.exceptions.ResourceNotFoundException;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MainService {

    private final SongsRepository songsRepo;

    public Song createSong(Song song) {
        Song savedSong = songsRepo.save(song);
        return savedSong;
    }

    public Song getSongById(Long songId) {
        Song foundSong = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song with id " + songId + " does not exist"));
        return foundSong;
    }

    public List<Song> getAllSongs(){
        List<Song> songs = songsRepo.findAll();
        return songs;
    } 

    public Song updateSong(Long songId, Song updatedSong) {
        Song songToEdit = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song with id " + songId + " does not exist"));
        
        songToEdit.setTitle(updatedSong.getTitle());
        songToEdit.setAlbum(updatedSong.getAlbum());
        songToEdit.setArtist(updatedSong.getArtist());
        songToEdit.setLength(updatedSong.getLength());

        Song savedSong = songsRepo.save(songToEdit);

        return savedSong;
    }

    public void deleteSong(Long songId) {

        // Currently managing the error with this exception but it can be deleted
        
        Song songToDelete = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song with id " + songId + " does not exist"));
        
        songsRepo.deleteById(songId);
    }
}
