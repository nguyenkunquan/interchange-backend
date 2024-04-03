package com.interchange.controller;

import com.interchange.service.ImageRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/image-room")
public class ImageRoomController {

    @Autowired
    private ImageRoomService imageRoomService;

    @GetMapping
    public ResponseEntity<?> getImageRoomByRoomId(@RequestParam int roomId) {
        return imageRoomService.getImageRoomByRoomId(roomId);
    }

    @GetMapping("/is-room-has-image")
    public ResponseEntity<?> isRoomHasImage(@RequestParam int roomId) {
        return imageRoomService.isRoomHasImage(roomId);
    }

}
