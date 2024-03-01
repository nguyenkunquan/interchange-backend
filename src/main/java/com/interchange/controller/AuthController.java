package com.interchange.controller;

import com.interchange.dto.ChangePasswordDTO;
import com.interchange.dto.ForgetPasswordDTO;
import com.interchange.dto.LoginDTO;
import com.interchange.dto.RegisterDTO;
import com.interchange.entities.User;
import com.interchange.repository.UserRepository;
import com.interchange.service.TwilioOTPService;
import com.interchange.service.UserService;
import com.interchange.service.impl.UserDetailServiceImpl;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDetailServiceImpl userDetailService;
    @Autowired
    private TwilioOTPService twilioOTPService;
    @Autowired
    private UserService userService;
    @PostMapping(value = "/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginDTO loginDTO) {
        try {
            String token = userService.login(loginDTO);
            return ResponseEntity.ok(token);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    @PostMapping(value = "/registration")
    public ResponseEntity<?> registerUser( @Valid @RequestBody RegisterDTO registerDTO) {
        try {
            if (Boolean.TRUE.equals(userRepository.existsByUserId(registerDTO.getUserId()))) {
                return new ResponseEntity<>("User ID had in the system", HttpStatus.CONFLICT);
            }
            if (Boolean.TRUE.equals(userRepository.existsByEmail(registerDTO.getEmail()))) {
                return new ResponseEntity<>("Email had in the system", HttpStatus.CONFLICT);
            }
            if (Boolean.TRUE.equals(userRepository.existsByPhoneNumber(registerDTO.getPhoneNumber()))) {
                return new ResponseEntity<>("Phone number had in the system", HttpStatus.CONFLICT);
            }
            if (!registerDTO.getPassword().equals(registerDTO.getRePassword())) {
                return new ResponseEntity<>("The Re Password doesn't match", HttpStatus.BAD_REQUEST);
            }
            if(!registerDTO.isOver18()) {
                return new ResponseEntity<>("You must more than 18 years old", HttpStatus.BAD_REQUEST);
            }
            twilioOTPService.sendOTP(registerDTO.getPhoneNumber());
            registerDTO.setOtp(twilioOTPService.getGenerateOTP());
            return new ResponseEntity<>("Send OTP successfully" + " OTP: " + registerDTO.getOtp(), HttpStatus.OK);
        } catch (ConstraintViolationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }
    @PostMapping("/RegisterOTPAuthentication")
    public ResponseEntity<?> authenticateOTPForRegister(@RequestBody RegisterDTO registerDTO) {
        try {
            if(registerDTO.getOtp() == null) {
                return new ResponseEntity<>("OTP can not null", HttpStatus.BAD_REQUEST);
            }
            if(twilioOTPService.verifyOTP(registerDTO.getOtp())) {
                return userService.registerUser(registerDTO);
            } else {
                return new ResponseEntity<>("Invalid OTP", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping("/forgetPassword")
    public ResponseEntity<?> forgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {

        if(Boolean.TRUE.equals(userRepository.existsByUserId(forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber())) ||
                Boolean.TRUE.equals(userRepository.existsByPhoneNumber(forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber())) ||
                Boolean.TRUE.equals(userRepository.existsByEmail(forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber()))) {
            String phoneNumber = userService.findPhoneNumber(forgetPasswordDTO);
            if(forgetPasswordDTO.getNewPassword().equals(forgetPasswordDTO.getReNewPassword())) {
                twilioOTPService.sendOTP(phoneNumber);
                return new ResponseEntity<>("Send OTP successfully", HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Re Password should match the Password", HttpStatus.BAD_REQUEST);
            }
        } else {
            return new ResponseEntity<>("Can not authorize user", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("ForgetPasswordOTPAuthentication")
    public ResponseEntity<?> authenticateOTPForForgetPassword(@RequestBody ForgetPasswordDTO forgetPasswordDTO) {
        if (forgetPasswordDTO.getOtp() == null) {
            return new ResponseEntity<>("Fail to send OTP", HttpStatus.CONFLICT);
        }
        if(twilioOTPService.verifyOTP(forgetPasswordDTO.getOtp())) {
            return userService.resetPassword(forgetPasswordDTO);
        }
        else {
            return new ResponseEntity<>("Fail to authenticate OTP", HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/upgrading")
    public ResponseEntity<?> updateUser(@RequestBody RegisterDTO registerDTO) {
        try {
            return userService.updateUser(registerDTO);
        }
        catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/profile/{userId}")
    public RegisterDTO showProfile(@PathVariable String userId) {
        return userService.getProfile(userId);
    }

    @PostMapping("/passwordChangingProcess")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordDTO changePasswordDTO) {
        try {
            return userService.changePassword(changePasswordDTO);
        } catch (Exception ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}