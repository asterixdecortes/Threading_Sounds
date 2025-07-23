package com.threadingsounds.Threading_Sounds.services;

import java.util.ArrayList;
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

    public PlaylistDto convertToDto(Playlist playlist) {
        PlaylistDto dto = new PlaylistDto();
        dto.setName(playlist.getName());
        dto.setDescription(playlist.getDescription());
        List<Long> songIds = playlist.getSongs().stream()
            .map(Song::getId)
            .collect(Collectors.toList());
        dto.setSongIds(songIds);
        return dto;
    }

    public Playlist convertToEntity(PlaylistDto dto) {
        Playlist playlist = new Playlist();
        playlist.setName(dto.getName());
        playlist.setDescription(dto.getDescription());

        List<Song> songs = new ArrayList<>();
        if (dto.getSongIds() != null) {
            for (Long songId : dto.getSongIds()) {
                Song song = songsRepo.findById(songId)
                    .orElseThrow(() -> new ResourceNotFoundException("Song not found with id " + songId));
                songs.add(song);
            }
        }
        playlist.setSongs(songs);
        return playlist;
    }

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

    public PlaylistDto getPlaylistById(Long id) {
        Playlist playlist = playlistRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id " + id));
        return convertToDto(playlist);
    }

    public List<PlaylistDto> getAllPlaylists() {
        return playlistRepo.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
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

    public void addSongToPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepo.findById(playlistId)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id " + playlistId));
        Song song = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song not found with id " + songId));

        if (!playlist.getSongs().contains(song)) {
            playlist.getSongs().add(song);
            playlistRepo.save(playlist);
        }
    }

    public void removeSongFromPlaylist(Long playlistId, Long songId) {
        Playlist playlist = playlistRepo.findById(playlistId)
            .orElseThrow(() -> new ResourceNotFoundException("Playlist not found with id " + playlistId));
        Song song = songsRepo.findById(songId)
            .orElseThrow(() -> new ResourceNotFoundException("Song not found with id " + songId));

        if (playlist.getSongs().contains(song)) {
            playlist.getSongs().remove(song);
            playlistRepo.save(playlist);
        }
    }
}
