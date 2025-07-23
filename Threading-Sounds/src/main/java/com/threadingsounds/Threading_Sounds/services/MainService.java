package com.threadingsounds.Threading_Sounds.services;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.threadingsounds.Threading_Sounds.dto.SongDto;
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

    public SongDto convertToDto(Song song) {
        SongDto dto = new SongDto();
        dto.setTitle(song.getTitle());
        dto.setLength(song.getLength());
        dto.setArtistId(song.getArtist().getId());
        if (song.getAlbum() != null) {
            dto.setAlbumId(song.getAlbum().getId());
        }
        return dto;
    }

    public Song convertToEntity(SongDto dto) {
        Artist artist = artistRepo.findById(dto.getArtistId())
            .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id " + dto.getArtistId()));

        Album album = null;
        if (dto.getAlbumId() != null) {
            album = albumRepo.findById(dto.getAlbumId())
                .orElseThrow(() -> new ResourceNotFoundException("Album not found with id " + dto.getAlbumId()));
        }
        Song song = new Song();
        song.setTitle(dto.getTitle());
        song.setLength(dto.getLength());
        song.setArtist(artist);
        song.setAlbum(album);
        return song;
    }

    // Create song, Album is optional
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
    public SongDto getSongById(Long id) {
        Song song = songsRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Song not found with id " + id));
        return convertToDto(song);
    }

    // Get all the info from all songs
    public List<SongDto> getAllSongs() {
        return songsRepo.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
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