package com.interchange.dto.AuthDTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

@Data
public class ForgetPasswordDTO {
    @NotBlank(message = "Can not null")
    private String userIdOrEmailOrPhoneNumber;
    @NotBlank(message = "New Password can not null")
    @Length(min = 8, message = "Password must be at least 8 characters")
    private String newPassword;
    @NotBlank(message = "Re New Password can not null")
    private String reNewPassword;
    @NotBlank(message = "OTP can not null")
    private String otp;
}