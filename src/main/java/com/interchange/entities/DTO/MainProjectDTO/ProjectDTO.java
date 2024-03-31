package com.interchange.entities.DTO.MainProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProjectDTO {
    private int projId;
    private String projName;
    private String projDescription;
    private int projectCategoryId;
    private int supplierId;
    private int quotationId;
    private List<RoomDTO> rooms;
}
