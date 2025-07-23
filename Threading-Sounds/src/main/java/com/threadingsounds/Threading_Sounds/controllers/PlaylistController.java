package com.threadingsounds.Threading_Sounds.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.threadingsounds.Threading_Sounds.dto.PlaylistDto;
import com.threadingsounds.Threading_Sounds.entities.Playlist;
import com.threadingsounds.Threading_Sounds.services.PlaylistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;

    @PostMapping
    public ResponseEntity<Playlist> createPlaylist(@RequestBody PlaylistDto dto) {
        Playlist saved = playlistService.createPlaylist(dto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<PlaylistDto> getPlaylistById(@PathVariable Long id) {
        PlaylistDto playlist = playlistService.getPlaylistById(id);
        return ResponseEntity.ok(playlist);
    }

    @GetMapping
    public ResponseEntity<List<PlaylistDto>> getAllPlaylists() {
        List<PlaylistDto> playlists = playlistService.getAllPlaylists();
        return ResponseEntity.ok(playlists);
    }

    @PutMapping("{id}")
    public ResponseEntity<Playlist> updatePlaylist(@PathVariable Long id, @RequestBody PlaylistDto dto) {
        return ResponseEntity.ok(playlistService.updatePlaylist(id, dto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deletePlaylist(@PathVariable Long id) {
        playlistService.deletePlaylist(id);
        return ResponseEntity.ok("Playlist deleted");
    }

    @PutMapping("{playlistId}/addSong/{songId}")
    public ResponseEntity<Void> addSongToPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.addSongToPlaylist(playlistId, songId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("{playlistId}/removeSong/{songId}")
    public ResponseEntity<Void> removeSongFromPlaylist(@PathVariable Long playlistId, @PathVariable Long songId) {
        playlistService.removeSongFromPlaylist(playlistId, songId);
        return ResponseEntity.noContent().build();
    }
}