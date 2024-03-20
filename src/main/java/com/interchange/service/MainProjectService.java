package com.interchange.service;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface MainProjectService {
    ResponseEntity<?> getMainProjectList(int status, int page);
    ResponseEntity<?> findMainProjectById(int id);
    ResponseEntity<?> getLastQuotationOfMainProject(int mainProjectId);
}
