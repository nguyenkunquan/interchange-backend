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
public class IsCustomized {
    @Id
    private int isCustomizedId;
    private boolean isCusLength;
    private boolean isCusWidth;
    private boolean isCusHeight;

    //    @OneToOne(mappedBy = "isCustomized")
    //    private CategoryProduct categoryProduct;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "isCustomizedId")
    private Set<CategoryProduct> categoryProducts = new HashSet<>();
}
