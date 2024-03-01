package com.interchange.repository;

import com.interchange.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {
//    @Query(value = "SELECT pj.proj_name, r.room_name, p.pro_name, rp.quantity FROM project pj\n" +
//                    "    JOIN room r ON pj.proj_id = ? and pj.proj_id = r.proj_id\n" +
//                    "    JOIN category_room cr ON r.room_category_id = cr.room_category_id\n" +
//                    "    JOIN category_style cs ON r.style_category_id = cs.style_category_id\n" +
//                    "    JOIN room_product rp ON r.room_id = rp.room_id\n" +
//                    "    JOIN product_detail pd ON rp.pro_detail_id = pd.pro_detail_id\n" +
//                    "    JOIN supplier_product sp ON pd.supplier_product_id = sp.supplier_product_id\n" +
//                    "    JOIN product p ON sp.pro_id = p.pro_id", nativeQuery = true)
//    List<Map<String , Objects>> findProjectRoomProductDetails(int projId);

}
