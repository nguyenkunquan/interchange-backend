package com.interchange.service;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

public interface BlogService {
    ResponseEntity<?> save(int blogId, String blogTitle, String blogContent, String staffId
            , boolean isDraft,int isThumbnailRadio, MultipartFile[] multipartFiles);
    ResponseEntity<?> findByBlogId(int postId);
    Boolean existsByPostId(int postId);
    ResponseEntity<?> getListBlogPagingBySearch(int page, String option, String search);
    ResponseEntity<?> deleteById(int postId);
    ResponseEntity<?> findAll();
    ResponseEntity<?> findById(int blogId);
}
