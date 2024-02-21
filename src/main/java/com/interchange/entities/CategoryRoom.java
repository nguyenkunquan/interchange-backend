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
public class CategoryRoom {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomCategoryId;
    private String categoryName;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "roomCategoryId")
    private Set<Room> rooms = new HashSet<>();
}
