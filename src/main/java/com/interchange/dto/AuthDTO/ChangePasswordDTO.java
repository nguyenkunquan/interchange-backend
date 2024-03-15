package com.interchange.dto.AuthDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
public class ChangePasswordDTO {
    @NotBlank(message = "The old password can not null")
    private String oldPassword;
    @NotBlank(message = "The new password can not null")
    @Length(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;
    @NotBlank(message = "The re password can not null")
    private String reNewPassword;
}