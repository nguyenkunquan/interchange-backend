package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.entities.DTO.MainProjectDTO.QuotationDTO;
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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Array;
import java.util.*;


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
    public ResponseEntity<?> getProjectCostByYear(int year) {
        try {
            List<Integer> total = new ArrayList<>();
            for(int i = 1; i<= 12; i++) {
                int projectCost = projectRepository.getTotalCost(year, i);
                total.add(projectCost);
            }

            return ResponseEntity.status(HttpStatus.OK).body(total);
        } catch (Exception ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while getting project cost by year");
        }
    }

    @Override
    public ResponseEntity<?> exportProject(int year) {
        try(Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");

            Row titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("STT");
            titleRow.createCell(1).setCellValue("Tên khách hàng");
            titleRow.createCell(2).setCellValue("Thời gian hoàn thành");
            titleRow.createCell(3).setCellValue("Giá trị dự án");
            titleRow.createCell(4).setCellValue("Ghi chú");
            List<Map<String, Object>> exportDTOS = projectRepository.exportProject(year);
            int rowcount = 1;
            double totalCost = 0;
            for(Map<String, Object> exportDTO : exportDTOS) {
                Row row = sheet.createRow(rowcount++);
                row.createCell(0).setCellValue(rowcount - 1);
                row.createCell(1).setCellValue(exportDTO.get("firstName").toString() +  exportDTO.get("lastName").toString());
                row.createCell(2).setCellValue(exportDTO.get("endDate").toString());
                row.createCell(3).setCellValue(exportDTO.get("projCost").toString());
                totalCost += (double) exportDTO.get("projCost");
            }
            Row row = sheet.createRow(rowcount + 1);
            row.createCell(0).setCellValue("Tổng doanh thu");
            row.createCell(3).setCellValue(totalCost);
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            workbook.write(outputStream);

            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=report.xlsx");

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .headers(headers)
                    .body(outputStream.toByteArray());

        } catch (IOException ex) {
            ex.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error while reporting project");
        }
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
