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
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomId;
    private String roomName;
    private String roomDescription;
    private int roomCategoryId;
    private int styleCategoryId;
    private double roomLength;
    private double roomWidth;
    private double roomHeight;
    private int projId;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "roomId")
    private Set<ImageRoom> imageRooms = new HashSet<>();
}
