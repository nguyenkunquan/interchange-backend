package com.interchange.controller;

import com.interchange.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
