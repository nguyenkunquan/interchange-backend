package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.DTO.MainProjectDTO.QuotationDTO;
import com.interchange.entities.DTO.MainProjectDTO.RoomDTO;
import com.interchange.entities.MainProject;
import com.interchange.entities.Project;
import com.interchange.entities.Quotation;
import com.interchange.repository.CategoryProjectRepository;
import com.interchange.repository.MainProjectRepository;
import com.interchange.repository.ProjectRepository;
import com.interchange.repository.QuotationRepository;
import com.interchange.service.ImageRoomService;
import com.interchange.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ProjectServiceImpl extends BaseResponse implements ProjectService {

    @Autowired
    private MainProjectRepository mainProjectRepository;
    @Autowired
    private ProjectRepository projectRepository;
    @Autowired
    private CategoryProjectRepository categoryProjectRepository;
    @Autowired
    private QuotationRepository quotationRepository;
    @Autowired
    private ImageRoomService imageRoomService;

    @Override
    public ResponseEntity<?> findById(int projectId) {
        //return getResponseEntity(projectRepository.findProjectRoomProductDetails(projectId));
        return getResponseEntity(projectRepository.findById(projectId));
    }

    @Override
    public ResponseEntity<?> findAllCategoryProject() {
        return getResponseEntity(categoryProjectRepository.findAll());
    }

    @Override
    public ResponseEntity<?> updateProject(QuotationDTO quotationDTO) {
        Quotation quotation = quotationRepository.findById(quotationDTO.getQuotationId()).get();
        quotation.setStatus(quotationDTO.getStatus());

        MainProject mainProject = mainProjectRepository.findById(quotation.getMainProject().getMainProjectId()).get();
        mainProject.setStatus(quotationDTO.getStatus());
        mainProjectRepository.save(mainProject);

        Project project = projectRepository.findById(quotation.getProject().getProjId()).get();
        quotationRepository.save(quotation);
        project.setProjName(quotationDTO.getProject().getProjName());
        project.setProjDes(quotationDTO.getProject().getProjDescription());
        for (int i = 0; i < quotationDTO.getProject().getRooms().size(); i++) {
            project.getRooms().get(i).setRoomName(quotationDTO.getProject().getRooms().get(i).getRoomName());
            project.getRooms().get(i).setRoomDescription(quotationDTO.getProject().getRooms().get(i).getRoomDescription());
        }
        return getResponseEntity(projectRepository.save(project));
    }

    @Override
    public ResponseEntity<?> updateImageRooms(int finalQuotationId, MultipartFile[] multipartFiles) {
        Quotation quotation = quotationRepository.findById(finalQuotationId).get();
        Project project = quotation.getProject();
        imageRoomService.save(project, multipartFiles);
        return getResponseEntity("Update image rooms success!");
    }

}
