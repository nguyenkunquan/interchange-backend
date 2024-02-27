package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.repository.CategoryRoomRepository;
import com.interchange.repository.ProductRepository;
import com.interchange.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ProductServiceImpl extends BaseResponse implements ProductService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    CategoryRoomRepository categoryRoomRepository;

    @Override
    public ResponseEntity<?> findAll() {
        return getResponseEntity(productRepository.findAll());
    }

    @Override
    public ResponseEntity<?> findAllProductDetailByRoomId(int roomId) {
        return getResponseEntity(productRepository.findAllProductDetailByRoomId(roomId));
    }

    @Override
    public ResponseEntity<?> inputCustomProduct(int proId, int supId) {
        return getResponseEntity(productRepository.inputCustomProduct(proId, supId));
    }

    @Override
    public ResponseEntity<?> isCustomizedByProId(int proId) {
        Map<String, Object> map = productRepository.isCustomizedByProId(proId);
        boolean check = false;
        if (map.get("is_cus_height").toString().equals("true")) {
            check = true;
            return getResponseEntity(check);
        }
        else return getResponseEntity(check);
    }

    @Override
    public ResponseEntity<?> findAllProductByRoomCategoryId(int roomCategoryId) {
        return getResponseEntity(categoryRoomRepository.findAllProductByRoomCategoryId(roomCategoryId));
    }
}
