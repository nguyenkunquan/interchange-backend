package com.interchange.entities;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonPropertyOrder({"status", "message", "data"})
public class MyResponse {
    private Object data;
    private int status;
    private String message;
}
