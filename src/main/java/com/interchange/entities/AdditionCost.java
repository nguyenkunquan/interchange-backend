package com.interchange.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class AdditionCost {
    @Id
    private int additionCostId;
    private double designFee;
    private double permittingFee;
    private double laborFee;
    private double equipmentFee;
    private double transportationFee;

    @OneToOne
    @JoinColumn(name = "projId", referencedColumnName = "projId")
    private Project project;
}
