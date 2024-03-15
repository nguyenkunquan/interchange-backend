package com.interchange.converter;

import com.interchange.dto.ManageCustomerAndStaffDTO.AddCustomerAndStaffDTO;
import com.interchange.dto.ManageCustomerAndStaffDTO.UpdateCustomerAndStaffDTO;
import com.interchange.entities.User;
import org.springframework.stereotype.Component;

@Component
public class CustomerAndStaffConverter {
    public User toUser(AddCustomerAndStaffDTO addCustomerAndStaffDTO) {
        User user = new User();
        user.setUserId(addCustomerAndStaffDTO.getUserId());
        user.setPassword(addCustomerAndStaffDTO.getPassword());
        user.setEmail(addCustomerAndStaffDTO.getEmail());
        user.setPhoneNumber(addCustomerAndStaffDTO.getPhoneNumber());
        user.setFirstName(addCustomerAndStaffDTO.getFirstName());
        user.setLastName(addCustomerAndStaffDTO.getLastName());
        user.setBirthDate(addCustomerAndStaffDTO.getBirthDate());
        user.setProvince(addCustomerAndStaffDTO.getProvince());
        user.setDistrict(addCustomerAndStaffDTO.getDistrict());
        user.setWard(addCustomerAndStaffDTO.getWard());
        user.setStreetAddress(addCustomerAndStaffDTO.getStreetAddress());
        return user;
    }
    public User toUser(UpdateCustomerAndStaffDTO updateCustomerAndStaffDTO, User user) {
        user.setUserId(updateCustomerAndStaffDTO.getUserId());
//        user.setPassword(updateCustomerAndStaffDTO.getPassword());
        user.setEmail(updateCustomerAndStaffDTO.getEmail());
        user.setPhoneNumber(updateCustomerAndStaffDTO.getPhoneNumber());
        user.setFirstName(updateCustomerAndStaffDTO.getFirstName());
        user.setLastName(updateCustomerAndStaffDTO.getLastName());
        user.setBirthDate(updateCustomerAndStaffDTO.getBirthDate());
        user.setProvince(updateCustomerAndStaffDTO.getProvince());
        user.setDistrict(updateCustomerAndStaffDTO.getDistrict());
        user.setWard(updateCustomerAndStaffDTO.getWard());
        user.setStreetAddress(updateCustomerAndStaffDTO.getStreetAddress());
        return user;
    }
}
