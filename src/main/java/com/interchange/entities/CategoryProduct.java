package com.interchange.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class CategoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int proCategoryId;
    private String categoryName;

    @JsonIgnore
    @OneToMany(mappedBy = "categoryProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<CategoryMaterial> categoryMaterials = new HashSet<>();
}
