package com.interchange.controller;

import com.interchange.entities.DTO.MainProjectDTO.MainProjectDTO;
import com.interchange.service.MainProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/main-project")
public class MainProjectController {

    @Autowired
    MainProjectService mainProjectService;

    @GetMapping("/was-approved")
    public ResponseEntity<?> getMainProjectWasApproved(@RequestParam int status, @RequestParam("requestTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate requestTime) {
        return mainProjectService.getMainProjectWasApproved(status, requestTime);
    }

    @GetMapping()
    public ResponseEntity<?> findMainProjectById(@RequestParam int mainProjectId) {
        return mainProjectService.findMainProjectById(mainProjectId);
    }

}
