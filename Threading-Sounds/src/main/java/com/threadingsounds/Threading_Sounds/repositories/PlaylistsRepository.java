package com.threadingsounds.Threading_Sounds.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.threadingsounds.Threading_Sounds.entities.Playlist;

@Repository
public interface PlaylistsRepository extends JpaRepository<Playlist, Long>{
}
