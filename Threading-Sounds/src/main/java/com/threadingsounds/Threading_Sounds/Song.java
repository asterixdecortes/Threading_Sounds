package com.threadingsounds.Threading_Sounds;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Data
@Table(name="songs")
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column (name = "title", nullable = false)
    private String title;
    
    @Column (name = "artist", nullable = false)
    private String artist;

    @Column (name = "album", nullable = false)
    private String album;

    @Column (name = "length", nullable = false)
    private int length;
}
