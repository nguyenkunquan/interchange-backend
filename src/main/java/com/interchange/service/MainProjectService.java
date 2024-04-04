package com.interchange.service;

import com.interchange.entities.DTO.MainProjectDTO.MainProjectDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.LocalDate;

public interface MainProjectService {
    ResponseEntity<?> getMainProjectList(int status, int page);
    ResponseEntity<?> findMainProjectById(int id);
    ResponseEntity<?> getLastQuotationOfMainProject(int mainProjectId);
    ResponseEntity<?> getPreQuotation(int mainProjectId, int preQuotationId);
    ResponseEntity<?> createMainProject(MainProjectDTO mainProjectDTO);
    ResponseEntity<?> getMainProjectListByCusId(String cusId);
    ResponseEntity<?> hasRequestIsWaiting(int mainProjectId);
    ResponseEntity<?> getFinalQuotation(int mainProjectId);
    ResponseEntity<?> saveMainProjectWithFirstQuotationHasInformation(MainProjectDTO mainProjectDTO);
}
