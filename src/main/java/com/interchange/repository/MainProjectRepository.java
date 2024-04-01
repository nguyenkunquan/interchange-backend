package com.interchange.repository;

import com.interchange.entities.MainProject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface MainProjectRepository extends JpaRepository<MainProject, Integer> {
    //    @Query(value = "SELECT mp.main_project_id FROM main_project mp " +
//            "JOIN quotation q ON mp.main_project_id = q.main_project_id " +
//            "WHERE q.status = 2 OR q.status = 3", nativeQuery = true)
//    HashSet<String> getMainProjectWasApproved();
//
//    @Query(value = "SELECT mp.main_project_id FROM main_project mp " +
//            "JOIN quotation q ON mp.main_project_id = q.main_project_id " +
//            "WHERE q.status = 3", nativeQuery = true)
//    HashSet<String> getMainProjectWasApprovedByStatusIs3();
    @Query(value = "SELECT * FROM main_project WHERE status = ?1 ", nativeQuery = true)
    Page<Map<String, Object>> findByStatus(int status, Pageable pageable);
    @Query(value = "SELECT * FROM main_project", nativeQuery = true)
    Page<Map<String, Object>> findAllByPage(Pageable pageable);

    @Query(value = "SELECT * FROM main_project WHERE customer_id = ?1 ", nativeQuery = true)
    List<Map<String, Object>> getMainProjectListByCusId(String cusId);
}
