package com.threadingsounds.Threading_Sounds;

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
        return (foundSong);
    }
}
