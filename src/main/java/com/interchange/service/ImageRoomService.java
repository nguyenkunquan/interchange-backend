package com.interchange.service;

import com.interchange.entities.Project;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface ImageRoomService {
    ResponseEntity<?> save(Project project, MultipartFile[] multipartFiles);
    ResponseEntity<?> getImageRoomByRoomId(int roomId);
    public ResponseEntity<?> isRoomHasImage(int roomId);
}
