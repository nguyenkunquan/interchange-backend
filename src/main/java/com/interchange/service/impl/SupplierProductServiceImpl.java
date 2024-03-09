package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.dto.SupplierProductDTO.UpdateUnitPriceDTO;
import com.interchange.entities.SupplierProduct;
import com.interchange.repository.SupplierProductRepository;
import com.interchange.service.SupplierProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SupplierProductServiceImpl extends BaseResponse implements SupplierProductService {
    @Autowired
    private SupplierProductRepository supplierProductRepository;
    @Override
    public ResponseEntity<?> listSupplierProduct(int proId) {
        return getResponseEntity(supplierProductRepository.listSupplierProduct(proId));
    }

    @Override
    public ResponseEntity<?> updateUnitPrice(int supProId, UpdateUnitPriceDTO updateUnitPriceDTO) {
        Optional<SupplierProduct> supplierProduct = supplierProductRepository.findById(supProId);
        if (supplierProduct.isPresent()) {
            supplierProduct.get().setUnitPrice(updateUnitPriceDTO.getUnitPrice());
            supplierProductRepository.save(supplierProduct.get());
            return getResponseEntity("Update unit price success");
        }
        else {
            return getResponseEntity("Supplier product not found");
        }
    }
}
