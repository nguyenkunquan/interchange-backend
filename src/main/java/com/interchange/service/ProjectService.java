package com.interchange.service;

import com.interchange.entities.DTO.MainProjectDTO.QuotationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

public interface ProjectService {
    ResponseEntity<?> findById(int projectId);
    ResponseEntity<?> findAllCategoryProject();
<<<<<<< HEAD

    ResponseEntity<?> exportProject();
=======
    ResponseEntity<?> updateProject(QuotationDTO quotationDTO);
    ResponseEntity<?> updateImageRooms(int finalQuotationId, MultipartFile[] multipartFiles);
>>>>>>> origin/main
}
