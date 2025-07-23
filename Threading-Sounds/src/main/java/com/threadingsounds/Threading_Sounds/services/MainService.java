package com.threadingsounds.Threading_Sounds.services;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.threadingsounds.Threading_Sounds.entities.Album;
import com.threadingsounds.Threading_Sounds.entities.Artist;
import com.threadingsounds.Threading_Sounds.entities.Song;
import com.threadingsounds.Threading_Sounds.exceptions.ResourceNotFoundException;
import com.threadingsounds.Threading_Sounds.repositories.AlbumsRepository;
import com.threadingsounds.Threading_Sounds.repositories.ArtistsRepository;
import com.threadingsounds.Threading_Sounds.repositories.SongsRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MainService {

    private static final Logger logger = LoggerFactory.getLogger(MainService.class);

    private final SongsRepository songsRepo;
    private final ArtistsRepository artistRepo;
    private final AlbumsRepository albumRepo;

    // Create song, Album is optional (opcional)
    public Song createSong(Song song, Long artistId, Long albumId) {
        Artist artist = artistRepo.findById(artistId)
            .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + artistId + " does not exist"));
        song.setArtist(artist);

        if (albumId != null) {
            Album album = albumRepo.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album with id " + albumId + " does not exist"));
            song.setAlbum(album);
        }
        logger.info("Creating song: " + song.getTitle());
        return songsRepo.save(song);
    }

    // Get all the info from a song
    public Song getSongById(Long songId) {
        return songsRepo.findById(songId)
            .orElseThrow(() -> {
            logger.error("Song with id " + songId + " not found");
            return new ResourceNotFoundException("Song with id " + songId + " does not exist");
        });
    }

    // Get all the info from all songs
    public List<Song> getAllSongs() {
        return songsRepo.findAll();
    }

    // Update a song, including artist or and song
    public Song updateSong(Long songId, Song updatedSong, Long artistId, Long albumId) {
        Song songToEdit = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song with id " + songId + " does not exist"));

        songToEdit.setTitle(updatedSong.getTitle());
        songToEdit.setLength(updatedSong.getLength());

        Artist artist = artistRepo.findById(artistId)
            .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + artistId + " does not exist"));
        songToEdit.setArtist(artist);

        if (albumId != null) {
            Album album = albumRepo.findById(albumId)
                .orElseThrow(() -> new ResourceNotFoundException("Album with id " + albumId + " does not exist"));
            songToEdit.setAlbum(album);
        } else {
            songToEdit.setAlbum(null);
        }

        return songsRepo.save(songToEdit);
    }

    public void deleteSong(Long songId) {
        Song songToDelete = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song with id " + songId + " does not exist"));
        songsRepo.delete(songToDelete);
    }
}