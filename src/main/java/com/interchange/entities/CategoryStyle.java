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
public class CategoryStyle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int styleCategoryId;
    private String styleName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "styleCategoryId")
    private Set<Room> rooms = new HashSet<>();
}
