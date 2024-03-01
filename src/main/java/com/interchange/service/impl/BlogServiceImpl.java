package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.Blog;
import com.interchange.entities.ImageBlog;
import com.interchange.repository.BlogRepository;
import com.interchange.service.BlogService;
import com.interchange.service.ImageBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class BlogServiceImpl extends BaseResponse implements BlogService {

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    ImageBlogService imageBlogService;

    @Override
    public ResponseEntity<?> save(int blogId, String blogTitle, String blogContent, String staffId, boolean isDraft, int isThumbnailRadio, MultipartFile[] multipartFiles) {
        Blog blog = new Blog();
        if(blogRepository.existsById(blogId))
            blog.setBlogId(blogId);
        blog.setBlogContent(blogContent);
        blog.setBlogTitle(blogTitle);
        blog.setStaffId(staffId);
        blog.setPostTime(new Date());
        blog.setDraft(isDraft);
        int bId = blogRepository.save(blog).getBlogId();
        imageBlogService.save(bId, multipartFiles, isThumbnailRadio);
        return getResponseEntity("Save post successful!");
    }

    @Override
    public ResponseEntity<?> findByBlogId(int blogId) {
        try {
            return getResponseEntity(blogRepository.findById(blogId).get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Boolean existsByPostId(int blogId) {
        return blogRepository.existsByBlogId(blogId);
    }

    @Override
    public ResponseEntity<?> getListBlogPagingBySearch(int page, String option, String search) {
        Pageable pageable = PageRequest.of(page, 4);
        Page<Blog> blogPage;
        switch (option) {
            case "all":
                try {
                    blogPage = blogRepository.findByBlogTitleContaining(search, pageable);
                } catch (Exception e) {
                    blogPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
                    return getResponseEntity(blogPage);
                }
                break;
            case "draft":
                try {
                    blogPage = blogRepository.findByBlogTitleContainingAndIsDraftIsTrue(search, pageable);
                } catch (Exception e) {
                    blogPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
                    return getResponseEntity(blogPage);
                }
                break;
            case "undraft":
                try {
                    blogPage = blogRepository.findByBlogTitleContainingAndIsDraftIsFalse(search, pageable);
                } catch (Exception e) {
                    blogPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
                    return getResponseEntity(blogPage);
                }
                break;
            default:
                blogPage = new PageImpl<>(Collections.emptyList(), pageable, 0);
                return getResponseEntity(blogPage);
        }
        return getResponseEntity(blogPage);
    }

    @Override
    public ResponseEntity<?> deleteById(int postId) {
        if(blogRepository.existsByBlogId(postId)) {
            List<ImageBlog> imageBlogList = imageBlogService.getListImageBlogByBlogId(postId);
            for (ImageBlog imageBlog : imageBlogList) {
                imageBlogService.deleteImageBlog(imageBlog);
            }
            blogRepository.deleteById(postId);
            return getResponseEntity("Delete blog successful!");
        }
        else return null;
    }

    @Override
    public ResponseEntity<?> findAll() {
        return getResponseEntity(blogRepository.findAll());
    }

    @Override
    public ResponseEntity<?> findById(int blogId) {
        return getResponseEntity(blogRepository.findById(blogId));
    }

}
