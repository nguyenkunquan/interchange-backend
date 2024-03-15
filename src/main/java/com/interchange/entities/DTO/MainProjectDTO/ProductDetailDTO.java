package com.interchange.entities.DTO.MainProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDetailDTO {
    private int proId;
    private double proLength;
    private double proWidth;
    private double proHeight;
    private int quantity;
    private double totalPrice;
}
