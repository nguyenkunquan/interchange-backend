package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.repository.CategoryProjectRepository;
import com.interchange.repository.ProjectRepository;
import com.interchange.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProjectServiceImpl extends BaseResponse implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CategoryProjectRepository categoryProjectRepository;

    @Override
    public ResponseEntity<?> findById(int projectId) {
        //return getResponseEntity(projectRepository.findProjectRoomProductDetails(projectId));
        return getResponseEntity(projectRepository.findById(projectId));
    }

    @Override
    public ResponseEntity<?> findAllCategoryProject() {
        return getResponseEntity(categoryProjectRepository.findAll());
    }

}
