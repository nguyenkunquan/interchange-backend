package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.dto.ExportDTO.ExportDTO;
import com.interchange.entities.Project;
import com.interchange.repository.CategoryProjectRepository;
import com.interchange.repository.ProjectRepository;
import com.interchange.service.ProjectService;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;


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

    @Override
    public ResponseEntity<?> exportProject() {
        try(Workbook workbook = new SXSSFWorkbook()) {
            Sheet sheet = workbook.createSheet("Report");

            Row titleRow = sheet.createRow(0);
            titleRow.createCell(0).setCellValue("STT");
            titleRow.createCell(1).setCellValue("Tên khách hàng");
            titleRow.createCell(2).setCellValue("Thời gian hoàn thành");
            titleRow.createCell(3).setCellValue("Giá trị dự án");
            titleRow.createCell(4).setCellValue("Ghi chú");
            List<Map<String, Object>> exportDTOS = projectRepository.exportProject();
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

}
