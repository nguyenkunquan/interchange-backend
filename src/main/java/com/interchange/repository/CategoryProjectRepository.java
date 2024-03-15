package com.interchange.repository;

import com.interchange.entities.CategoryProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProjectRepository extends JpaRepository<CategoryProject, Integer> {
}
