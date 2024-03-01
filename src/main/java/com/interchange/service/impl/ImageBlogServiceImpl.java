package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.Blog;
import com.interchange.entities.ImageBlog;
import com.interchange.repository.BlogRepository;
import com.interchange.repository.ImageBlogRepository;
import com.interchange.service.BlogService;
import com.interchange.service.ImageBlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class ImageBlogServiceImpl extends BaseResponse implements ImageBlogService {

    @Autowired
    ImageBlogRepository imageBlogRepository;
    @Autowired
    BlogRepository blogRepository;

    private ImageBlog convertFile(MultipartFile multipartFile) {
        ImageBlog imageBlog = new ImageBlog();
        try {
            imageBlog.setFileName(multipartFile.getOriginalFilename());
            imageBlog.setContentType(multipartFile.getContentType());
            imageBlog.setContent(multipartFile.getBytes());
            return imageBlog;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<?> save(int blogId, MultipartFile[] multipartFiles, int isThumbnailRadio) {
        ImageBlog imageBlog = new ImageBlog();
        Blog blog = blogRepository.findById(blogId).get();
        int count = 1;
        for (MultipartFile multipartFile : multipartFiles) {
            imageBlog = convertFile(multipartFile);
            imageBlog.setBlog(blog);
            imageBlog.setThumbnail(false);
            if(count == isThumbnailRadio) imageBlog.setThumbnail(true);
            count++;
            imageBlogRepository.save(imageBlog);
        }
        return getResponseEntity("Save file successful!");
    }

    @Override
    public List<ResponseEntity<?>> getImageBlogByBlogId(int blogId) {
        try {
            List<ImageBlog> imageBlogs = imageBlogRepository.findByBlog_BlogId(blogId);
            List<ResponseEntity<?>> responseEntityList = new ArrayList<>();
            for (ImageBlog imageBlog: imageBlogs) {
                responseEntityList.add(getResponseEntityFile(imageBlog));
            }
            return responseEntityList;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<ImageBlog> getListImageBlogByBlogId(int blogId) {
        return imageBlogRepository.findByBlog_BlogId(blogId);
    }

    @Override
    public ResponseEntity<?> deleteImageBlog(ImageBlog imageBlog) {
        imageBlogRepository.delete(imageBlog);
        return getResponseEntity("Delete image blog successful!");
    }

    @Override
    public ResponseEntity<?> findByBlogIdAndIsThumbnailIsTrue(int blogId) {
        return getResponseEntityFile(imageBlogRepository.findByBlog_BlogIdAndIsThumbnailIsTrue((blogId)));
    }

}
