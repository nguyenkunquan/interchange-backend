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

}
