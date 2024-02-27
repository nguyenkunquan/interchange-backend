package com.interchange.repository;

import com.interchange.entities.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BlogRepository extends JpaRepository<Blog, Integer> {
    Boolean existsByBlogId(int blogId);
    Page<Blog> findByBlogTitleContaining(String blogTitle, Pageable pageable);
    Page<Blog> findByBlogTitleContainingAndIsDraftIsTrue(String blogTitle, Pageable pageable);
    Page<Blog> findByBlogTitleContainingAndIsDraftIsFalse(String blogTitle, Pageable pageable);

}
