package com.interchange.converter;

import com.interchange.dto.AuthDTO.RegisterDTO;
import com.interchange.dto.AuthDTO.UpdateUserDTO;
import com.interchange.entities.Role;
import com.interchange.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserConverter {

    public User toUser(RegisterDTO registerDTO) {
        User user = new User();
        user.setUserId(registerDTO.getUserId());
        user.setPassword(registerDTO.getPassword());
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
        user.setPassword(registerDTO.getPassword());
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
    public User toUser(UpdateUserDTO updateUserDTO, User user) {
        user.setEmail(updateUserDTO.getEmail());
        user.setPhoneNumber(updateUserDTO.getPhoneNumber());
        user.setFirstName(updateUserDTO.getFirstName());
        user.setLastName(updateUserDTO.getLastName());
        user.setBirthDate(updateUserDTO.getBirthDate());
        user.setProvince(updateUserDTO.getProvince());
        user.setDistrict(updateUserDTO.getDistrict());
        user.setWard(updateUserDTO.getWard());
        user.setStreetAddress(updateUserDTO.getStreetAddress());
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

}