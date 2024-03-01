package com.interchange.repository;

import com.interchange.entities.ImageBlog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ImageBlogRepository extends JpaRepository<ImageBlog, Integer> {
    List<ImageBlog> findByBlog_BlogId(int blogId);
    ImageBlog findByBlog_BlogIdAndIsThumbnailIsTrue(int blogId);
}
