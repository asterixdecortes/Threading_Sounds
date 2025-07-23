package com.threadingsounds.Threading_Sounds.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.threadingsounds.Threading_Sounds.dto.PlaylistDto;
import com.threadingsounds.Threading_Sounds.entities.Playlist;
import com.threadingsounds.Threading_Sounds.entities.Song;
import com.threadingsounds.Threading_Sounds.exceptions.ResourceNotFoundException;
import com.threadingsounds.Threading_Sounds.repositories.PlaylistsRepository;
import com.threadingsounds.Threading_Sounds.repositories.SongsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PlaylistService {

    private final PlaylistsRepository playlistRepo;
    private final SongsRepository songsRepo;

    public Playlist createPlaylist(PlaylistDto dto) {
        Playlist playlist = new Playlist();
        playlist.setName(dto.getName());
        playlist.setDescription(dto.getDescription());

        if (dto.getSongIds() != null) {
            List<Song> songs = dto.getSongIds().stream()
                .map(id -> songsRepo.findById(id)
                        .orElseThrow(() -> new ResourceNotFoundException("Song with id " + id + " not found")))
                .collect(Collectors.toList());
            playlist.setSongs(songs);
        }

        return playlistRepo.save(playlist);
    }

    public Playlist getPlaylistById(Long id) {
        return playlistRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist with id " + id + " not found"));
    }

    public List<Playlist> getAllPlaylists() {
        return playlistRepo.findAll();
    }

    public Playlist updatePlaylist(Long id, PlaylistDto dto) {
        Playlist playlist = playlistRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist with id " + id + " not found"));

        playlist.setName(dto.getName());
        playlist.setDescription(dto.getDescription());

        if (dto.getSongIds() != null) {
            List<Song> songs = dto.getSongIds().stream()
                .map(songId -> songsRepo.findById(songId)
                        .orElseThrow(() -> new ResourceNotFoundException("Song with id " + songId + " not found")))
                .collect(Collectors.toList());
            playlist.setSongs(songs);
        }

        return playlistRepo.save(playlist);
    }

    public void deletePlaylist(Long id) {
        Playlist playlist = playlistRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist with id " + id + " not found"));
        playlistRepo.delete(playlist);
    }

    public Playlist addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepo.findById(playlistId)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist with id " + playlistId + " not found"));

        Song song = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song with id " + songId + " not found"));

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
        }

        return playlistRepo.save(playlist);
    }

    public Playlist removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepo.findById(playlistId)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist with id " + playlistId + " not found"));

        Song song = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song with id " + songId + " not found"));

        playlist.getSongs().remove(song);
        return playlistRepo.save(playlist);
    }
}
