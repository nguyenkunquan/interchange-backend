package com.interchange.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
public class ChangePasswordDTO implements Serializable {
    @Length(max = 10, message = "The User ID must be less than 10 characters")
    @NotBlank(message = "The User ID can not null")
    private String userId;
    @NotBlank(message = "The old password can not null")
    private String oldPassword;
    @NotBlank(message = "The new password can not null")
    private String newPassword;
    @NotBlank(message = "The re password can not null")
    private String reNewPassword;
}