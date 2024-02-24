package com.interchange.entities;

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

//    @ManyToOne
//    private Room room;
//
//    @ManyToOne
//    private ProductDetail productDetail;

    private int roomId;
    private int proDetailId;

    private int quantity;
    @Column(nullable = true)
    private double totalPrice;
}
