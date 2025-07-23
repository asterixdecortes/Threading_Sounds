package com.threadingsounds.Threading_Sounds.dto;

import java.util.List;

import lombok.Data;

@Data
public class PlaylistDto {
    private String name;
    private String description;
    private List<Long> songIds;
}
