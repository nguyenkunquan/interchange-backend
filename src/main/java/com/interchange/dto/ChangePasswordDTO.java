package com.interchange.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data @NoArgsConstructor @AllArgsConstructor
public class ChangePasswordDTO implements Serializable {
    private String userId;

    private String oldPassword;

    private String newPassword;

    private String reNewPassword;
}
