package com.threadingsounds.Threading_Sounds.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import com.threadingsounds.Threading_Sounds.dto.SongDto;
import com.threadingsounds.Threading_Sounds.entities.Song;
import com.threadingsounds.Threading_Sounds.services.MainService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/songs")
public class MainController {

    private final MainService songsService;

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(status);
    }

    // Creates a song using the DTO
    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody SongDto songDto) {
        Song song = new Song();
        song.setTitle(songDto.getTitle());
        song.setLength(songDto.getLength());

        Song saved = songsService.createSong(song, songDto.getArtistId(), songDto.getAlbumId());
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<SongDto> getSongById(@PathVariable Long id) {
        SongDto song = songsService.getSongById(id);
        return ResponseEntity.ok(song);
    }

    @GetMapping
    public ResponseEntity<List<SongDto>> getAllSongs() {
        List<SongDto> songs = songsService.getAllSongs();
        return ResponseEntity.ok(songs);
    }

    // Updates a song using DTO
    @PutMapping("{id}")
    public ResponseEntity<Song> updateSong(@PathVariable("id") Long songId, @RequestBody SongDto songDto) {
        Song updated = new Song();
        updated.setTitle(songDto.getTitle());
        updated.setLength(songDto.getLength());

        Song savedSong = songsService.updateSong(songId, updated, songDto.getArtistId(), songDto.getAlbumId());
        return ResponseEntity.ok(savedSong);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteSong(@PathVariable("id") Long songId) {
        songsService.deleteSong(songId);
        return ResponseEntity.ok("Song deleted");
    }

    @GetMapping("/force-error")
    public String triggerError() {
        String test = null;
        return test.toString(); // This throws a NullPointerException
    }

}