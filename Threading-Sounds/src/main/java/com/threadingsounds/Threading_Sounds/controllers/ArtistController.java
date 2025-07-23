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

import com.threadingsounds.Threading_Sounds.dto.ArtistDto;
import com.threadingsounds.Threading_Sounds.entities.Artist;
import com.threadingsounds.Threading_Sounds.services.ArtistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/artists")
public class ArtistController {

    private final ArtistService artistService;

    @PostMapping
    public ResponseEntity<Artist> createArtist(@RequestBody ArtistDto artistDto) {
        Artist savedArtist = artistService.createArtist(artistDto);
        return new ResponseEntity<>(savedArtist, HttpStatus.CREATED);
    }

    @GetMapping("{id}")
    public ResponseEntity<Artist> getArtistById(@PathVariable Long id) {
        Artist artist = artistService.getArtistById(id);
        return ResponseEntity.ok(artist);
    }

    @GetMapping
    public ResponseEntity<List<Artist>> getAllArtists() {
        return ResponseEntity.ok(artistService.getAllArtists());
    }

    @PutMapping("{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable Long id, @RequestBody ArtistDto artistDto) {
        Artist updatedArtist = artistService.updateArtist(id, artistDto);
        return ResponseEntity.ok(updatedArtist);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteArtist(@PathVariable Long id) {
        artistService.deleteArtist(id);
        return ResponseEntity.ok("Artist with id " + id + " deleted");
    }
}
