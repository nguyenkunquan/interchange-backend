package com.interchange.controller;


import com.interchange.entities.ImageBlog;
import com.interchange.service.BlogService;
import com.interchange.service.ImageBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/blog")
public class BlogController {

    @Autowired
    BlogService blogService;
    @Autowired
    ImageBlogService imageBlogService;

    @GetMapping
    public ResponseEntity<?> findAll() {
        return blogService.findAll();
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestParam int blogId, @RequestParam String blogTitle
            , @RequestParam String blogContent, @RequestParam String staffId
            , @RequestParam boolean isDraft, @RequestParam int isThumbnailRadio
            , @RequestParam MultipartFile[] multipartFiles) {
        return blogService.save(blogId, blogTitle, blogContent, staffId, isDraft, isThumbnailRadio, multipartFiles);
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam int page,
                                    @RequestParam String option,
                                    @RequestParam String search) {
        return blogService.getListBlogPagingBySearch(page, option, search);
    }

    @PostMapping("/delete")
    public ResponseEntity<?> deleteById(@RequestParam int blogId) {
        return blogService.deleteById(blogId);
    }

    @GetMapping("/thumbnail")
    public ResponseEntity<?> findByBlogIdAndIsThumbnailIsTrue(@RequestParam int blogId) {
        return imageBlogService.findByBlogIdAndIsThumbnailIsTrue(blogId);
    }

    @GetMapping("/image")
    public List<ResponseEntity<?>> getImageBlogByBlogId(@RequestParam int blogId){
        return imageBlogService.getImageBlogByBlogId((blogId));
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findById(@RequestParam int blogId) {
        return blogService.findById(blogId);
    }

}
