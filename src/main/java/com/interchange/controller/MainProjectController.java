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

    @GetMapping("/list")
    public ResponseEntity<?> getMainProjectList(@RequestParam int status, @RequestParam int page) {
        return mainProjectService.getMainProjectList(status, page);
    }

    @GetMapping()
    public ResponseEntity<?> findMainProjectById(@RequestParam int mainProjectId) {
        return mainProjectService.findMainProjectById(mainProjectId);
    }

    @GetMapping("/last-quotation")
    public ResponseEntity<?> getLastQuotationOfMainProject(@RequestParam int mainProjectId) {
        return mainProjectService.getLastQuotationOfMainProject(mainProjectId);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createMainProject(@RequestBody MainProjectDTO mainProjectDTO) {
        return mainProjectService.createMainProject(mainProjectDTO);
    }

    @GetMapping("list-by-cusId")
    public ResponseEntity<?> getMainProjectListByCusId(@RequestParam String cusId) {
        return mainProjectService.getMainProjectListByCusId(cusId);
    }

}
