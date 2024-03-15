package com.interchange.repository;

import com.interchange.entities.CategoryRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface CategoryRoomRepository extends JpaRepository<CategoryRoom, Integer> {
    @Query(value = "SELECT p.pro_id, p.pro_name\n" +
            "FROM category_room cr JOIN product p\n" +
            "ON cr.room_category_id = p.room_category_id and cr.room_category_id = ?", nativeQuery = true)
    List<Map<String, Objects>> findAllProductByRoomCategoryId(int roomCategoryId);

    CategoryRoom  findCategoryRoomByCategoryName(String categoryName);
    CategoryRoom findCategoryRoomByRoomCategoryId(int roomCategoryId);
}
