package com.interchange.repository;

import com.interchange.entities.Quotation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Repository
public interface QuotationRepository extends JpaRepository<Quotation, Integer> {

    @Query(value = "SELECT mp.main_project_id, q.quotation_id, u.first_name, u.last_name, u.phone_number, q.request_time, q.content_request_quotation, q.status " +
            "FROM user u " +
            "JOIN main_project mp ON u.user_id = mp.customer_id " +
            "JOIN quotation q ON mp.main_project_id = q.main_project_id " +
            "WHERE q.request_time = :requestTime and q.status = 1", nativeQuery = true)
    List<Map<String, Objects>> findAllPendingQuotationByTime(@Param("requestTime") LocalDate requestTime);

    @Query(value = "SELECT mp.main_project_id, q.quotation_id, mp.customer_id, mp.staff_id, u.first_name, u.last_name, u.phone_number, q.request_time, q.content_request_quotation, q.content_response, q.status, q.pre_quotation_id\n" +
            "FROM user u JOIN main_project mp ON u.user_id = mp.customer_id\n" +
            "            JOIN quotation q ON mp.main_project_id = q.main_project_id and q.quotation_id = ?", nativeQuery = true)
    Map<String, Object> findQuotationById(int quotationId);

    @Query(value = "SELECT * FROM quotation q JOIN main_project mp " +
            "WHERE q.main_project_id = mp.main_project_id and q.status = ?1 ", nativeQuery = true)
    Page<Map<String, Object>> findQuotationListByStatus(int status, Pageable pageable);

    @Query(value = "SELECT COUNT(*) " +
            "FROM quotation q " +
            "WHERE q.status = ?1", nativeQuery = true)
    Integer countQuotationByStatus(int status);

}
