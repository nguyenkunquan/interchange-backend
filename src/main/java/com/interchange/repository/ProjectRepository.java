package com.interchange.repository;

import com.interchange.entities.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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

    @Query(value = "SELECT mp.main_project_id, q.quotation_id, u.first_name, u.last_name, u.phone_number, q.request_time, q.content_request_quotation, q.status " +
            "FROM user u " +
            "JOIN main_project mp ON u.user_id = mp.customer_id " +
            "JOIN quotation q ON mp.main_project_id = q.main_project_id " +
            "WHERE q.request_time = :requestTime and q.status = 1", nativeQuery = true)
    List<Map<String, Objects>> findAllPendingQuotationByTime(@Param("requestTime") LocalDate requestTime);

    @Query(value = "SELECT u.first_name AS firstName, u.last_name AS lastName, pj.end_date AS endDate, pj.proj_cost AS projCost " +
            "FROM project pj " +
            "JOIN quotation q ON pj.quotation_id = q.quotation_id " +
            "JOIN main_project mp ON q.main_project_id = mp.main_project_id " +
            "JOIN user u ON u.user_id = mp.customer_id " +
            "WHERE YEAR(pj.end_date) = ?1", nativeQuery = true)
    List<Map<String, Object>> exportProject(int year);

    @Query(value = "select COALESCE(SUM(pj.proj_cost), 0) as total_cost " +
            "FROM project pj " +
            "WHERE YEAR(pj.end_date) = ?1 AND MONTH(pj.end_date) = ?2", nativeQuery = true)
    Integer getTotalCost(int year, int month);


}
