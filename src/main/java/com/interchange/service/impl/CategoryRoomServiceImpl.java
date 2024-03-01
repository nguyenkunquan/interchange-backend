package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.repository.CategoryRoomRepository;
import com.interchange.service.CategoryRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CategoryRoomServiceImpl extends BaseResponse implements CategoryRoomService {

    @Autowired
    CategoryRoomRepository categoryRoomRepository;

    @Override
    public ResponseEntity<?> findAll() {
        return getResponseEntity(categoryRoomRepository.findAll());
    }
}
