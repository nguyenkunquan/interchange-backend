package com.interchange.entities.DTO.MainProjectDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoomDTO {
    private int roomId;
    private String roomName;
    private String roomDescription;
    private int roomCategoryId;
    List<ProductDetailDTO> productDetailList;
}
