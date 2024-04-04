package com.interchange.service;

import com.interchange.entities.DTO.MainProjectDTO.MainProjectDTO;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface QuotationService {
    ResponseEntity<?> findAllPendingQuotationByTime(LocalDate requestTime);
    ResponseEntity<?> findQuotationById(int quotationId);
    ResponseEntity<?> saveQuotation(MainProjectDTO mainProjectDTO);
    ResponseEntity<?> updateQuotation(MainProjectDTO mainProjectDTO);
    ResponseEntity<?> findQuotationEagerById(int quotationId);
    ResponseEntity<?> findQuotationListByStatus(int status, int page);
    ResponseEntity<?> updateQuotationStatus(int quotationId, int newStatus, String contentResponse);
    ResponseEntity<?> createRequestQuotation(MainProjectDTO mainProjectDTO);
    ResponseEntity<?> countQuotationByStatus();
    ResponseEntity<?> findQuotationByStatusAndProjectCategory(int status, int projectCategoryId);
}
