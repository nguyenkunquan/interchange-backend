package com.interchange.converter;

import com.interchange.dto.AuthDTO.UpdateUserDTO;
import com.interchange.dto.ManageCustomerDTO.AddCustomerDTO;
import com.interchange.dto.ManageCustomerDTO.UpdateCustomerDTO;
import com.interchange.entities.Role;
import com.interchange.entities.User;
import org.springframework.stereotype.Component;

@Component
public class CustomerConverter {
    public User toUser(AddCustomerDTO addCustomerDTO) {
        User user = new User();
        user.setUserId(addCustomerDTO.getUserId());
        user.setPassword(addCustomerDTO.getPassword());
        user.setEmail(addCustomerDTO.getEmail());
        user.setPhoneNumber(addCustomerDTO.getPhoneNumber());
        user.setFirstName(addCustomerDTO.getFirstName());
        user.setLastName(addCustomerDTO.getLastName());
        user.setBirthDate(addCustomerDTO.getBirthDate());
        user.setRole(Role.CUSTOMER);
        user.setProvince(addCustomerDTO.getProvince());
        user.setDistrict(addCustomerDTO.getDistrict());
        user.setWard(addCustomerDTO.getWard());
        user.setStreetAddress(addCustomerDTO.getStreetAddress());
        return user;
    }
    public User toUser(UpdateCustomerDTO updateCustomerDTO, User user) {
        user.setUserId(updateCustomerDTO.getUserId());
        user.setPassword(updateCustomerDTO.getPassword());
        user.setEmail(updateCustomerDTO.getEmail());
        user.setPhoneNumber(updateCustomerDTO.getPhoneNumber());
        user.setFirstName(updateCustomerDTO.getFirstName());
        user.setLastName(updateCustomerDTO.getLastName());
        user.setBirthDate(updateCustomerDTO.getBirthDate());
        user.setProvince(updateCustomerDTO.getProvince());
        user.setDistrict(updateCustomerDTO.getDistrict());
        user.setWard(updateCustomerDTO.getWard());
        user.setStreetAddress(updateCustomerDTO.getStreetAddress());
        return user;
    }
}
