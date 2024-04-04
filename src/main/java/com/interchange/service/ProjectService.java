package com.interchange.service;

import com.interchange.entities.DTO.MainProjectDTO.QuotationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {
    ResponseEntity<?> findById(int projectId);
    ResponseEntity<?> findAllCategoryProject();
    ResponseEntity<?> exportProject(int year);
    ResponseEntity<?> updateProject(QuotationDTO quotationDTO);
    ResponseEntity<?> updateImageRooms(int finalQuotationId, MultipartFile[] multipartFiles);
    ResponseEntity<?> getProjectCostByYear(int year);
}
