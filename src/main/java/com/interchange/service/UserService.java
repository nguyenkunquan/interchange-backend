package com.interchange.service;

import com.interchange.dto.ChangePasswordDTO;
import com.interchange.dto.ForgetPasswordDTO;
import com.interchange.dto.LoginDTO;
import com.interchange.dto.RegisterDTO;
import org.springframework.http.ResponseEntity;


public interface UserService {
    String login(LoginDTO loginDTO);
    ResponseEntity<?> registerUser(RegisterDTO registerDTO);
    ResponseEntity<?> updateUser(RegisterDTO registerDTO);
    RegisterDTO getProfile(String userId);
    ResponseEntity<?> changePassword(ChangePasswordDTO changePasswordDTO);
    ResponseEntity<?> resetPassword(ForgetPasswordDTO forgetPasswordDTO);
    String findPhoneNumber(ForgetPasswordDTO forgetPasswordDTO);


}