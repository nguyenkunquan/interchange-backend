package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.repository.MeasureUnitRepository;
import com.interchange.service.MeasureUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MeasureUnitServiceImpl extends BaseResponse implements MeasureUnitService {
    @Autowired
    private MeasureUnitRepository measureUnitRepository;
    @Override
    public ResponseEntity<?> findAll() {
        return getResponseEntity(measureUnitRepository.findAll());
    }
}
