package com.threadingsounds.Threading_Sounds.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.threadingsounds.Threading_Sounds.dto.ArtistDto;
import com.threadingsounds.Threading_Sounds.entities.Artist;
import com.threadingsounds.Threading_Sounds.exceptions.ResourceNotFoundException;
import com.threadingsounds.Threading_Sounds.repositories.ArtistsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ArtistService {

    private final ArtistsRepository artistRepo;

    // Creates and artist
    public Artist createArtist(ArtistDto artistDto) {
        Artist artist = new Artist();
        artist.setName(artistDto.getName());
        return artistRepo.save(artist);
    }

    // Gets all the information from an artist using id
    public Artist getArtistById(Long id) {
        return artistRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + id + " not found"));
    }

    // Gets all the information from all the artists
    public List<Artist> getAllArtists() {
        return artistRepo.findAll();
    }

    // Changes the info from an artist
    public Artist updateArtist(Long id, ArtistDto updatedData) {
        Artist artist = artistRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + id + " not found"));

        artist.setName(updatedData.getName());
        return artistRepo.save(artist);
    }

    // Deletes an artist
    public void deleteArtist(Long id) {
        Artist artist = artistRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + id + " not found"));

        artistRepo.delete(artist);
    }
}
