package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.dto.AuthDTO.*;
import com.interchange.jwt.JwtTokenUtil;
import com.interchange.converter.UserConverter;
import com.interchange.entities.User;
import com.interchange.repository.UserRepository;
import com.interchange.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl extends BaseResponse implements UserService {

    private final UserRepository userRepository;

    private final UserConverter userConverter;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    private final JwtTokenUtil jwtTokenUtil;

    public UserServiceImpl(UserRepository userRepository,
                           UserConverter userConverter,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtTokenUtil jwtTokenUtil) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtTokenUtil = jwtTokenUtil;
    }
    @Override
    public ResponseEntity<?> login(LoginDTO loginDTO) {
        User user = userRepository.findByUserIdOrEmailOrPhoneNumber(
                        loginDTO.getUserIdOrPhoneNumberOrEmail(),
                        loginDTO.getUserIdOrPhoneNumberOrEmail(),
                        loginDTO.getUserIdOrPhoneNumberOrEmail())
                .orElseThrow(() -> new UsernameNotFoundException("User can't found"));

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                loginDTO.getUserIdOrPhoneNumberOrEmail(),loginDTO.getPassword()
        );
        authenticationManager.authenticate(authenticationToken);
        List<Map<String, String>> response = new ArrayList<>();
        Map<String, String> map = new HashMap<>();
        map.put("userId", user.getUserId());
        map.put("token", jwtTokenUtil.generateToken(user));
        map.put("name", user.getFirstName() + " " + user.getLastName());
        map.put("role", user.getRole().toString());
        response.add(map);
        return getResponseEntity(response);
    }
    @Override
    public ResponseEntity<?> registerUser(RegisterDTO registerDTO) {
        try {
            User user = userConverter.toUser(registerDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<>("Register successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @Override
    public ResponseEntity<?> updateUser(String userId, UpdateUserDTO updateUserDTO) {
        User user = userRepository.findFirstByUserId(userId);
        user = userConverter.toUser(updateUserDTO, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Update successfully!", HttpStatus.OK);
    }
    @Override
    public RegisterDTO getProfile(String userId) {
        User user = userRepository.findFirstByUserId(userId);
        return userConverter.toRegisterDTO(user);
    }
    @Override
    public ResponseEntity<?> changePassword(String userId, ChangePasswordDTO changePasswordDTO) {
        User user = userRepository.findFirstByUserId(userId);
        if(!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())){
            return new ResponseEntity<>("Invalid Old Password!", HttpStatus.BAD_REQUEST);
        }
        if(!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getReNewPassword())) {
            return new ResponseEntity<>("Invalid re password", HttpStatus.BAD_REQUEST);
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);

        return new ResponseEntity<>("Change Password successfully", HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> resetPassword(ForgetPasswordDTO forgetPasswordDTO) {
        User user = userRepository
                .findByUserIdOrEmailOrPhoneNumber(
                        forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber(),
                        forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber(),
                        forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber()
                ).orElseThrow(() -> new UsernameNotFoundException("User can't found "));
        user.setPassword(passwordEncoder.encode(forgetPasswordDTO.getNewPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Create new password successfully", HttpStatus.OK);
    }
    @Override
    public String findPhoneNumber(ForgetPasswordDTO forgetPasswordDTO) {
        User user = userRepository
                .findByUserIdOrEmailOrPhoneNumber(
                        forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber(),
                        forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber(),
                        forgetPasswordDTO.getUserIdOrEmailOrPhoneNumber()
                ).orElseThrow(() -> new UsernameNotFoundException("User can't found "));
        return user.getPhoneNumber();
    }
}