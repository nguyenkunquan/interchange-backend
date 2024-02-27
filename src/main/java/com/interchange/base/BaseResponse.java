package com.interchange.base;

import com.interchange.entities.ImageBlog;
import com.interchange.entities.MyResponse;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

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
        return ResponseEntity.status(200)
                .contentType(MediaType.parseMediaType(imageBlog.getContentType()))
                .body(imageBlog.getContent());
    }

}
