package com.interchange.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RoomProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int roomProductId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "roomId")
    private Room room;

    @ManyToOne
    @JoinColumn(name = "proDetailId")
    private ProductDetail productDetail;

    private int quantity;
    @Column(nullable = true)
    private double totalPrice;
}
