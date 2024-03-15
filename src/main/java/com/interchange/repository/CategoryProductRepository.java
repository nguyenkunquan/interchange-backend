package com.interchange.repository;

import com.interchange.entities.CategoryProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct, Integer> {
    CategoryProduct findCategoryProductByCategoryName(String categoryName);

    CategoryProduct findFirstByProCategoryId(int proCategoryId);
}
