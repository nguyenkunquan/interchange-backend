package com.interchange.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class UnitPrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierProductId;
    private double unitPrice;

    @OneToOne
    @MapsId
    @JoinColumn(name = "supplier_product_id")
    private SupplierProduct supplierProduct;

}
