package com.interchange.controller;

import com.interchange.entities.DTO.MainProjectDTO.MainProjectDTO;
import com.interchange.service.QuotationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/quotation")
public class QuotationController {

    @Autowired
    QuotationService quotationService;

    @GetMapping("/pendingQuotation")
    public ResponseEntity<?> findAllPendingQuotationByTime(@RequestParam("requestTime") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate requestTime) {
        return quotationService.findAllPendingQuotationByTime(requestTime);
    }
    @GetMapping("/countQuotationByStatus")
    public ResponseEntity<?> countQuotationByStatus() {
        return quotationService.countQuotationByStatus();
    }

    @GetMapping("/findById")
    public ResponseEntity<?> findQuotationById(@RequestParam int quotationId) {
        return quotationService.findQuotationById(quotationId);
    }

    @GetMapping("/findEagerById")
    public ResponseEntity<?> findQuotationEagerById(@RequestParam int quotationId) {
        return quotationService.findQuotationEagerById(quotationId);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveQuotation(@RequestBody MainProjectDTO mainProjectDTO) {
        return quotationService.saveQuotation(mainProjectDTO);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateQuotation(@RequestBody MainProjectDTO mainProjectDTO) {
        return quotationService.updateQuotation(mainProjectDTO);
    }

    @GetMapping("/pagination-list")
    public ResponseEntity<?> findQuotationListByStatus(@RequestParam int status, @RequestParam int page) {
        return quotationService.findQuotationListByStatus(status, page);
    }

    @PostMapping("/update-status")
    public ResponseEntity<?> updateQuotationStatus(@RequestParam int quotationId, @RequestParam int newStatus, @RequestParam String contentResponse) {
        return quotationService.updateQuotationStatus(quotationId, newStatus, contentResponse);
    }

    @PostMapping("/create-requestQuotation")
    public ResponseEntity<?> createRequestQuotation(@RequestBody MainProjectDTO mainProjectDTO) {
        return quotationService.createRequestQuotation(mainProjectDTO);
    }

    @GetMapping("/find-quotation-by-status-and-project-category")
    public ResponseEntity<?> findQuotationByStatusAndProjectCategory(@RequestParam int status, @RequestParam int projectCategoryId) {
        return quotationService.findQuotationByStatusAndProjectCategory(status, projectCategoryId);
    }

}
