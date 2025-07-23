package com.threadingsounds.Threading_Sounds.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.threadingsounds.Threading_Sounds.dto.AlbumDto;
import com.threadingsounds.Threading_Sounds.entities.Album;
import com.threadingsounds.Threading_Sounds.entities.Artist;
import com.threadingsounds.Threading_Sounds.exceptions.ResourceNotFoundException;
import com.threadingsounds.Threading_Sounds.repositories.AlbumsRepository;
import com.threadingsounds.Threading_Sounds.repositories.ArtistsRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlbumService {

    private final AlbumsRepository albumRepo;
    private final ArtistsRepository artistRepo;

    public AlbumDto convertToDto(Album album) {
        AlbumDto dto = new AlbumDto();
        dto.setTitle(album.getTitle());
        dto.setArtistId(album.getArtist().getId());
        return dto;
    }

    public Album convertToEntity(AlbumDto dto) {
        Artist artist = artistRepo.findById(dto.getArtistId())
            .orElseThrow(() -> new ResourceNotFoundException("Artist not found"));

        Album album = new Album();
        album.setTitle(dto.getTitle());
        album.setArtist(artist);
        return album;
    }


    public Album createAlbum(AlbumDto albumDto) {
        Artist artist = artistRepo.findById(albumDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + albumDto.getArtistId() + " not found"));

        Album album = new Album();
        album.setTitle(albumDto.getTitle());
        album.setArtist(artist);

        return albumRepo.save(album);
    }

    public AlbumDto getAlbumById(Long id) {
        Album album = albumRepo.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Album not found"));
        return convertToDto(album);
    }

    public List<AlbumDto> getAllAlbums() {
        return albumRepo.findAll().stream()
            .map(this::convertToDto)
            .collect(Collectors.toList());
    }

    public Album updateAlbum(Long id, AlbumDto albumDto) {
        Album album = albumRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album with id " + id + " not found"));

        album.setTitle(albumDto.getTitle());

        Artist artist = artistRepo.findById(albumDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + albumDto.getArtistId() + " not found"));
        album.setArtist(artist);

        return albumRepo.save(album);
    }

    public void deleteAlbum(Long id) {
        Album album = albumRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album with id " + id + " not found"));
        albumRepo.delete(album);
    }
}
