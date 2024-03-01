package com.interchange.service;

import org.springframework.http.ResponseEntity;

public interface ProjectService {
    ResponseEntity<?> findById(int projectId);
}
