package com.interchange.dto.AuthDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginDTO {
    @NotBlank
    private String userIdOrPhoneNumberOrEmail;
    @NotBlank
    private String password;
}