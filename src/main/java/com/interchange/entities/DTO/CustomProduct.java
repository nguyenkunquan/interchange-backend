package com.interchange.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomProduct {
    private int supplierProductId;
    private double length;
    private double width;
    private double height;
    private double unitPrice;
    private int quantity;
    private double totalPrice;
}
