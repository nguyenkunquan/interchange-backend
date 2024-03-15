package com.interchange.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SupplierProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supplierProductId;

    @ManyToOne
    @JoinColumn(name = "supId")
    private Supplier supplier;

    @ManyToOne
    @JoinColumn(name = "proId")
    private Product product;

    @JsonIgnore
    @OneToMany(mappedBy = "supplierProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ProductDetail> productDetails = new HashSet<>();

    private double unitPrice;
}
