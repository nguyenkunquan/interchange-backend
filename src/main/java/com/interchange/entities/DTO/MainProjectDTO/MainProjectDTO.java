package com.interchange.entities.DTO.MainProjectDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MainProjectDTO {
    private int mainProjectId;
    private String customerId;
    private String staffId;
    private int status;
    private List<QuotationDTO> quotations;
}

