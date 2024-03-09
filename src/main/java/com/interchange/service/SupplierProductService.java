package com.interchange.service;

import com.interchange.dto.SupplierProductDTO.UpdateUnitPriceDTO;
import org.springframework.http.ResponseEntity;

public interface SupplierProductService {
    ResponseEntity<?> listSupplierProduct(int proId);
    ResponseEntity<?> updateUnitPrice(int supProId, UpdateUnitPriceDTO updateUnitPriceDTO);
}
