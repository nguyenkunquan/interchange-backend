package com.interchange.service;

import com.interchange.dto.AuthDTO.*;
import org.springframework.http.ResponseEntity;


public interface UserService {
    ResponseEntity<?> login(LoginDTO loginDTO);
    ResponseEntity<?> registerUser(RegisterDTO registerDTO);
    ResponseEntity<?> updateUser(String userId, UpdateUserDTO updateUserDTO);
    RegisterDTO getProfile(String userId);
    ResponseEntity<?> changePassword(String userId, ChangePasswordDTO changePasswordDTO);
    ResponseEntity<?> resetPassword(ForgetPasswordDTO forgetPasswordDTO);
    String findPhoneNumber(ForgetPasswordDTO forgetPasswordDTO);

}