package com.interchange.entities;

import jakarta.persistence.*;
//import jakarta.validation.constraints.Pattern;
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
    //@Pattern(regexp = "^(0[35789]\\d{8})$", message = "Invalid format phone")
    private String supPhone;
    private String supAddress;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "supId")
    private Set<SupplierProduct> supplierProducts = new HashSet<>();
}
