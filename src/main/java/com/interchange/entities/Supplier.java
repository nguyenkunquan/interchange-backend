package com.interchange.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
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
public class Supplier {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int supId;
    private String supName;
    @Pattern(regexp = "^(0[35789]\\d{8})$", message = "Invalid format phone")
    private String supPhone;
    private String supAddress;

//    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "suppliers")
//    private Set<Product> products;

    @OneToMany(mappedBy = "supplier", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<SupplierProduct> supplierProducts = new HashSet<>();
}