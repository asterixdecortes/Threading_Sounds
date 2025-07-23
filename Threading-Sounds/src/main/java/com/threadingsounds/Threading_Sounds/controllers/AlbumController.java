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

import com.threadingsounds.Threading_Sounds.dto.AlbumDto;
import com.threadingsounds.Threading_Sounds.entities.Album;
import com.threadingsounds.Threading_Sounds.services.AlbumService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/albums")
public class AlbumController {

    private final AlbumService albumService;

    @PostMapping
    public ResponseEntity<Album> createAlbum(@RequestBody AlbumDto albumDto) {
        Album saved = albumService.createAlbum(albumDto);
        return new ResponseEntity<>(saved, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long id) {
        return ResponseEntity.ok(albumService.getAlbumById(id));
    }

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return ResponseEntity.ok(albumService.getAllAlbums());
    }

    @PutMapping("{id}")
    public ResponseEntity<Album> updateAlbum(@PathVariable Long id, @RequestBody AlbumDto albumDto) {
        return ResponseEntity.ok(albumService.updateAlbum(id, albumDto));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long id) {
        albumService.deleteAlbum(id);
        return ResponseEntity.ok("Album with id " + id + " deleted");
    }
}
