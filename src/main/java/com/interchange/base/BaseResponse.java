package com.interchange.base;

import com.interchange.entities.ImageBlog;
import com.interchange.entities.ImageRoom;
import com.interchange.entities.MyResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

public class BaseResponse {
    protected ResponseEntity<?> getResponseEntity(Object data) {
        return ResponseEntity.ok(getResponse(data));
    }

    private MyResponse getResponse(Object data) {
        MyResponse myResponse = new MyResponse();
        myResponse.setData(data);
        myResponse.setStatus(200);
        myResponse.setMessage("Success!");
        return myResponse;
    }


    //File
    protected ResponseEntity<?> getResponseEntityFile(ImageBlog imageBlog) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(imageBlog.getContentType()));
        headers.set("fileName", imageBlog.getFileName());
        headers.set("isThumbnail", String.valueOf(imageBlog.isThumbnail()));
        return ResponseEntity.status(200)
                .headers(headers)
                .body(imageBlog.getContent());
    }

    protected ResponseEntity<?> getResponseEntityRoomFile(ImageRoom imageRoom) {
        if(imageRoom == null) {
            return ResponseEntity.status(404).body(null);
        }
        return ResponseEntity.status(200)
                .contentType(MediaType.parseMediaType(imageRoom.getContentType()))
                .body(imageRoom.getContent());
    }

}
