package com.interchange.entities;

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
public class CategoryMaterial {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryMaterialId;

    @ManyToOne
    @JoinColumn(name = "proCategoryId")
    private CategoryProduct categoryProduct;

    @ManyToOne
    @JoinColumn(name = "materialId")
    private Material material;

    @OneToMany(mappedBy = "categoryMaterial", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Product> products = new HashSet<>();
}
