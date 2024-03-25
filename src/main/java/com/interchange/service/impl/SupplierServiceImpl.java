package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.converter.SupplierConverter;
import com.interchange.dto.SupplierDTO.SupplierDTO;
import com.interchange.entities.Product;
import com.interchange.entities.Supplier;
import com.interchange.entities.SupplierProduct;
import com.interchange.repository.ProductRepository;
import com.interchange.repository.SupplierProductRepository;
import com.interchange.repository.SupplierRepository;
import com.interchange.service.SupplierService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class SupplierServiceImpl extends BaseResponse implements SupplierService {
    @Autowired
    SupplierProductRepository supplierProductRepository;
    @Autowired
    SupplierRepository supplierRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    SupplierConverter supplierConverter;
    @Override
    public ResponseEntity<?> findAllSupplier() {
        return getResponseEntity(supplierRepository.findAll());
    }
    @Override
    public ResponseEntity<?> findAllSupplierProduct(int supplierId) {
        return getResponseEntity(supplierProductRepository.listSupplierProductBySupId(supplierId));
    }

    @Override
    @Transactional
    public ResponseEntity<?> addSuppler(SupplierDTO supplierDTO) {
        try {
            Supplier supplier = supplierConverter.toSupplier(supplierDTO);
            supplierRepository.save(supplier);
            addSupplierProduct(supplierDTO, supplier);
            return getResponseEntity("Add supplier successfully");
        } catch (Exception e) {
            return getResponseEntity("Add supplier failed");
        }
    }

    @Override
    @Transactional
    public ResponseEntity<?> updateSupplier(int supplierId, SupplierDTO supplierDTO) {
        try {
            Supplier supplier = supplierRepository.getFirstBySupId(supplierId);
            supplier.setSupName(supplierDTO.getSupplierName());
            supplier.setSupAddress(supplierDTO.getSupplierAddress());
            supplier.setSupPhone(supplierDTO.getSupplierPhone());
            supplierRepository.save(supplier);
            for(Map.Entry<Integer, Double> entry : supplierDTO.getUnitPrices().entrySet()){
                SupplierProduct supplierProduct = supplierProductRepository.getFirstByProIdAndSupId(entry.getKey(), supplierId);
                supplierProduct.setUnitPrice(entry.getValue());
                supplierProductRepository.save(supplierProduct);
            }
            return getResponseEntity("Update supplier successfully");
        } catch (Exception e) {
            return getResponseEntity("Update supplier failed");
        }
    }

    @Override
    public ResponseEntity<?> findSupplierById(int supplierId) {
        return getResponseEntity(supplierRepository.getFirstBySupId(supplierId));
    }

    private void addSupplierProduct(SupplierDTO supplierDTO, Supplier supplier) {
        for (Map.Entry<Integer, Double> entry : supplierDTO.getUnitPrices().entrySet()) {
            Product product = productRepository.findFirstByProId(entry.getKey());
            SupplierProduct supplierProduct = new SupplierProduct();
            supplierProduct.setProduct(product);
            supplierProduct.setSupplier(supplier);
            supplierProduct.setUnitPrice(entry.getValue());
            supplierProductRepository.save(supplierProduct);
        }
    }




}
