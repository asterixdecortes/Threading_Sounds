package com.threadingsounds.Threading_Sounds;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SongsRepository extends JpaRepository<Song, Long>{
}
