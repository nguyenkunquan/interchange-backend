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
    private int proCategoryId;
    private int materialId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "categoryMaterialId")
    private Set<Product> products = new HashSet<>();
}
