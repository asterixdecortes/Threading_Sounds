package com.threadingsounds.Threading_Sounds;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/songs")
public class MainController {


    private final MainService songsService;

    //Actuator is also working in /actuator/health, needs configuration if we want more precise metrics and info
    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> healthCheck() {
        Map<String, Object> status = new HashMap<>();
        status.put("status", "UP");
        status.put("timestamp", LocalDateTime.now());
        return ResponseEntity.ok(status);
    }

    
    @PostMapping
    public ResponseEntity<Song> createSong(@RequestBody Song song) {
        Song savedSong = songsService.createSong(song);
        return new ResponseEntity<>(savedSong, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Song> getSongById(@PathVariable("id") Long songId){
        Song foundSong = songsService.getSongById(songId);
        return ResponseEntity.ok(foundSong);
    }

    public void deleteSong(int id) {
        //TODO, take from SERVICE
    }
}
