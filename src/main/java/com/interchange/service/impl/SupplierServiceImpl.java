package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.repository.SupplierRepository;
import com.interchange.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SupplierServiceImpl extends BaseResponse implements SupplierService {

    @Autowired
    SupplierRepository supplierRepository;

    @Override
    public ResponseEntity<?> findAll() {
        return getResponseEntity(supplierRepository.findAll());
    }
}
