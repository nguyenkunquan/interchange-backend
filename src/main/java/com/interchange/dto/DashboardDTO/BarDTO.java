package com.interchange.dto.DashboardDTO;

import lombok.Data;

import java.util.List;

@Data
public class BarDTO {
    private String name;
    private List<Double> data;
}
