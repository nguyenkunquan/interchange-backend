package com.interchange.entities.DTO.MainProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class QuotationDTO {
    private int quotationId;
    private Date requestTime;
    private int status;
    private String contentRequestQuotation;
    private ProjectDTO project;
    private int preQuotationId;
}
