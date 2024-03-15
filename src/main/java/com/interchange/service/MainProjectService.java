package com.interchange.service;

import org.springframework.http.ResponseEntity;

import java.time.LocalDate;

public interface MainProjectService {
    ResponseEntity<?> getMainProjectWasApproved(int status, LocalDate requestTime);
    ResponseEntity<?> findMainProjectById(int id);
}
