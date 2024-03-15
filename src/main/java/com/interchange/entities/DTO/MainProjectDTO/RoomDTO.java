package com.interchange.entities.DTO.MainProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private int roomId;
    private String roomName;
    private int roomCategoryId;
    List<ProductDetailDTO> productDetailList;
}
