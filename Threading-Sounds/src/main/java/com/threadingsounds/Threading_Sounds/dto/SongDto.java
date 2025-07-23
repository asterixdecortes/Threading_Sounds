package com.threadingsounds.Threading_Sounds.dto;

import lombok.Data;

@Data
public class SongDto {
    private String title;
    private int length;
    private Long artistId;
    private Long albumId; // Optional
}