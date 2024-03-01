package com.interchange.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgetPasswordDTO {
    @NotBlank(message = "Can not null")
    private String userIdOrEmailOrPhoneNumber;
    @NotBlank(message = "New Password can not null")
    private String newPassword;
    @NotBlank(message = "Re New Password can not null")
    private String reNewPassword;
    @NotBlank(message = "OTP can not null")
    private String otp;
}