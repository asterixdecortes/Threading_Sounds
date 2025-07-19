package com.threadingsounds.Threading_Sounds;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/songs")
public class MainController {

    @GetMapping("/helloworld")
    public String helloworld() {
        return "Hello World";
    }

    @PostMapping
    public Song createSong(Song song) {
        //TODO, take from SERVICE
        return song;
    }

    public void deleteSong(int id) {
        //TODO, take from SERVICE
    }
}
