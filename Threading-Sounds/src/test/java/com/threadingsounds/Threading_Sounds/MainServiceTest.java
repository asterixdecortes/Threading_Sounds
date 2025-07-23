package com.threadingsounds.Threading_Sounds;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import com.threadingsounds.Threading_Sounds.dto.SongDto;
import com.threadingsounds.Threading_Sounds.entities.Album;
import com.threadingsounds.Threading_Sounds.entities.Artist;
import com.threadingsounds.Threading_Sounds.entities.Song;
import com.threadingsounds.Threading_Sounds.exceptions.ResourceNotFoundException;
import com.threadingsounds.Threading_Sounds.repositories.AlbumsRepository;
import com.threadingsounds.Threading_Sounds.repositories.ArtistsRepository;
import com.threadingsounds.Threading_Sounds.repositories.SongsRepository;
import com.threadingsounds.Threading_Sounds.services.MainService;

@ExtendWith(MockitoExtension.class)
public class MainServiceTest {

    @Mock
    private SongsRepository songsRepo;

	@Mock 
	private ArtistsRepository artistsRepo;

	@Mock 
	private AlbumsRepository albumsRepo;

    @InjectMocks
    private MainService mainService;

    @Test
    void createSong_shouldReturnSavedSong() {
        Artist artist = new Artist(1L, "John Lennon", new ArrayList<>(), new ArrayList<>());
    	Album album = new Album(1L, "Imagine", artist, new ArrayList<>());
    	Song song = new Song(null, "Imagine", artist, album, 183, new ArrayList<>());
    	Song saved = new Song(1L, "Imagine", artist, album, 183, new ArrayList<>());

        when(artistsRepo.findById(1L)).thenReturn(Optional.of(artist));
    	when(albumsRepo.findById(1L)).thenReturn(Optional.of(album));
    	when(songsRepo.save(song)).thenReturn(saved);

        Song result = mainService.createSong(song, 1L, 1L);

        assertEquals(saved, result);
    }

    @Test
    void getSongById_existingId_shouldReturnSongDto() {
        Artist artist = new Artist(1L, "John Lennon", new ArrayList<>(), new ArrayList<>());
        Album album = new Album(1L, "Imagine", artist, new ArrayList<>());

        Song song = new Song(1L, "Imagine", artist, album, 183, new ArrayList<>());

        when(songsRepo.findById(1L)).thenReturn(Optional.of(song));

        SongDto result = mainService.getSongById(1L);

        assertEquals("Imagine", result.getTitle());
        assertEquals(183, result.getLength());
        assertEquals(1L, result.getArtistId());
        assertEquals(1L, result.getAlbumId());
    }

    @Test
    void getSongById_nonExistingId_shouldThrowException() {
        when(songsRepo.findById(99L)).thenReturn(Optional.empty());

        ResourceNotFoundException thrown = assertThrows(ResourceNotFoundException.class, () -> mainService.getSongById(99L));

		assertEquals("Song not found with id 99", thrown.getMessage());
    }

    @Test
    void deleteSong_existingId_shouldCallDelete() {
        Artist artist = new Artist(1L, "John Lennon", new ArrayList<>(), new ArrayList<>());
        Album album = new Album(1L, "Imagine", artist, new ArrayList<>());
        Song song = new Song(1L, "Imagine", artist, album, 183, new ArrayList<>());

        when(songsRepo.findById(1L)).thenReturn(Optional.of(song));

        mainService.deleteSong(1L);

        verify(songsRepo).delete(song);
    }
}
