package com.threadingsounds.Threading_Sounds.services;

import java.util.List;
import java.util.stream.Collectors;

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

    // Entity to DTO
    public ArtistDto convertToDto(Artist artist) {
        ArtistDto dto = new ArtistDto();
        dto.setName(artist.getName());
        return dto;
    }

    // DTO to entity
    public Artist convertToEntity(ArtistDto dto) {
        Artist artist = new Artist();
        artist.setName(dto.getName());
        return artist;
    }

    // Creates and artist
    public Artist createArtist(ArtistDto artistDto) {
        Artist artist = new Artist();
        artist.setName(artistDto.getName());
        return artistRepo.save(artist);
    }

    // Gets all the information from an artist using id
    public ArtistDto getArtistById(Long id) {
        Artist artist = artistRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Artist not found with id " + id));
        return convertToDto(artist);
    }

    // Gets all the information from all the artists
    public List<ArtistDto> getAllArtists() {
        return artistRepo.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
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
