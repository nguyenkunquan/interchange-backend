package com.interchange.controller;

import com.interchange.entities.DTO.MainProjectDTO.QuotationDTO;
import com.interchange.entities.DTO.MainProjectDTO.RoomDTO;
import com.interchange.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping()
    public ResponseEntity<?> findById(@RequestParam int projId){
        return projectService.findById(projId);
    }

    @GetMapping("/categoryProject")
    public ResponseEntity<?> findAllCategoryProject() {
        return projectService.findAllCategoryProject();
    }

<<<<<<< HEAD
    @GetMapping("/export")
    public ResponseEntity<?> exportProject() {
        return projectService.exportProject();
=======
    @PostMapping("/update")
    public ResponseEntity<?> updateProject(@RequestBody QuotationDTO quotationDTO) {
        return projectService.updateProject(quotationDTO);
    }

    @PostMapping("/update-image-rooms")
    public ResponseEntity<?> updateImageRooms(@RequestParam int finalQuotationId, @RequestParam MultipartFile[] multipartFiles) {
        return projectService.updateImageRooms(finalQuotationId, multipartFiles);
>>>>>>> origin/main
    }

}
