package com.interchange.entities;

import com.interchange.entities.ImageProduct;
import com.interchange.entities.SupplierProduct;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proId;
    private String proName;
    private int categoryMaterialId;
    private String proDescription;
    private String proColor;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "proId")
    private Set<ImageProduct> imageProducts = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "proId")
    private Set<SupplierProduct> supplierProducts = new HashSet<>();



}
