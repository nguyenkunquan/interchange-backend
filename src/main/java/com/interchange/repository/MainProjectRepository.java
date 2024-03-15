package com.interchange.repository;

import com.interchange.entities.MainProject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashSet;

@Repository
public interface MainProjectRepository extends JpaRepository<MainProject, Integer> {
    @Query(value = "SELECT mp.main_project_id FROM main_project mp " +
            "JOIN quotation q ON mp.main_project_id = q.main_project_id " +
            "WHERE (q.status = 2 OR q.status = 3) and q.request_time = :requestTime", nativeQuery = true)
    HashSet<String> getMainProjectWasApproved(@Param("requestTime") LocalDate requestTime);

    @Query(value = "SELECT mp.main_project_id FROM main_project mp " +
            "JOIN quotation q ON mp.main_project_id = q.main_project_id " +
            "WHERE q.status = 3 and q.request_time = :requestTime", nativeQuery = true)
    HashSet<String> getMainProjectWasApprovedByStatusIs3(@Param("requestTime") LocalDate requestTime);
}
