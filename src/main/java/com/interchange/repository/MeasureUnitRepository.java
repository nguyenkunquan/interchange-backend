package com.interchange.repository;

import com.interchange.entities.MeasureUnit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MeasureUnitRepository extends JpaRepository<MeasureUnit, Integer> {
//    MeasureUnit findMeasureUnitByMeasureUnitNameAndCusLength(String measureUnitName, boolean cusLength);

    MeasureUnit findFirstByMeasureUnitId(int measureUnitId);
}