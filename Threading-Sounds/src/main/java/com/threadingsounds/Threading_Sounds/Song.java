package com.threadingsounds.Threading_Sounds;

import jakarta.persistence.Entity;

@Entity
public class Song {
    private int id;
    private String title;
    private String artist;
    private String album;
    private int duration;
}
