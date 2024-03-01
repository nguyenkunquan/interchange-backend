package com.interchange.service;

import com.interchange.entities.ImageBlog;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageBlogService {
    ResponseEntity<?> save(int postId, MultipartFile[] multipartFiles, int isThumbnailRadio);
    List<ResponseEntity<?>> getImageBlogByBlogId(int blogId);
    List<ImageBlog> getListImageBlogByBlogId(int blogId);
    ResponseEntity<?> deleteImageBlog(ImageBlog imageBlog);
    ResponseEntity<?> findByBlogIdAndIsThumbnailIsTrue(int blogId);
}
