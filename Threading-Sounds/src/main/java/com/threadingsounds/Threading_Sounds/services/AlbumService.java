package com.threadingsounds.Threading_Sounds.services;

import java.util.List;

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

    public Album createAlbum(AlbumDto albumDto) {
        Artist artist = artistRepo.findById(albumDto.getArtistId())
                .orElseThrow(() -> new ResourceNotFoundException("Artist with id " + albumDto.getArtistId() + " not found"));

        Album album = new Album();
        album.setTitle(albumDto.getTitle());
        album.setArtist(artist);

        return albumRepo.save(album);
    }

    public Album getAlbumById(Long id) {
        return albumRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Album with id " + id + " not found"));
    }

    public List<Album> getAllAlbums() {
        return albumRepo.findAll();
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
