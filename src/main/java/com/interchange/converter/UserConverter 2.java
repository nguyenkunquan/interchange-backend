package com.interchange.converter;

import com.interchange.dto.ChangePasswordDTO;
import com.interchange.dto.ForgetPasswordDTO;
import com.interchange.dto.RegisterDTO;
import com.interchange.entities.Role;
import com.interchange.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User toUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setUserId(registerDTO.getUserId());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setBirthDate(registerDTO.getBirthDate());
        user.setRole(Role.CUSTOMER);
        user.setProvince(registerDTO.getProvince());
        user.setDistrict(registerDTO.getDistrict());
        user.setWard(registerDTO.getWard());
        user.setStreetAddress(registerDTO.getStreetAddress());
        return user;
    }
    public User toUser(RegisterDTO registerDTO, User user) {
        user.setUserId(registerDTO.getUserId());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setPhoneNumber(registerDTO.getPhoneNumber());
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setBirthDate(registerDTO.getBirthDate());
        user.setRole(Role.CUSTOMER);
        user.setProvince(registerDTO.getProvince());
        user.setDistrict(registerDTO.getDistrict());
        user.setWard(registerDTO.getWard());
        user.setStreetAddress(registerDTO.getStreetAddress());
        return user;
    }

    public RegisterDTO toRegisterDTO(User user) {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUserId(user.getUserId());
        registerDTO.setPassword(user.getPassword());
        registerDTO.setEmail(user.getEmail());
        registerDTO.setPhoneNumber(user.getPhoneNumber());
        registerDTO.setFirstName(user.getFirstName());
        registerDTO.setLastName(user.getLastName());
        registerDTO.setBirthDate(user.getBirthDate());
        registerDTO.setProvince(user.getProvince());
        registerDTO.setDistrict(user.getDistrict());
        registerDTO.setWard(user.getWard());
        registerDTO.setStreetAddress(user.getStreetAddress());
        return registerDTO;
    }

    public User toUser(ChangePasswordDTO changePasswordDTO, User user) {
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        return user;
    }
    public User toUser(ForgetPasswordDTO forgetPasswordDTO, User user) {
        user.setPassword(passwordEncoder.encode(forgetPasswordDTO.getNewPassword()));
        return user;
    }
}
