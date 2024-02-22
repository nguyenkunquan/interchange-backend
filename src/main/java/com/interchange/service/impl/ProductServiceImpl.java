package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.repository.ProductRepository;
import com.interchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl extends BaseResponse implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public ResponseEntity<?> getAll() {
        return getResponseEntity(productRepository.findAll());
    }
}
