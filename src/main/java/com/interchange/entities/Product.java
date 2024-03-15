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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proId;
    @Column(unique = true)
    private String proName;
    private String proDescription;
    private String proColor;

    @JsonIgnore
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }, fetch = FetchType.EAGER
    )
    @JoinColumn(name = "categoryMaterialId")
    private CategoryMaterial categoryMaterial;

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<ImageProduct> imageProducts = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<SupplierProduct> supplierProducts = new HashSet<>();

    @JsonIgnore
    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }, fetch = FetchType.EAGER
    )
    @JoinColumn(name = "roomCategoryId")
    private CategoryRoom categoryRoom;

    @ManyToOne(
            cascade = {
                    CascadeType.PERSIST, CascadeType.MERGE,
                    CascadeType.DETACH, CascadeType.REFRESH
            }, fetch = FetchType.EAGER
    )
    @JoinColumn(name = "measureUnitId")
    private MeasureUnit measureUnit;
}
