package com.interchange.service;

import com.interchange.dto.ManageCustomerAndStaffDTO.AddCustomerAndStaffDTO;
import com.interchange.dto.ManageCustomerAndStaffDTO.UpdateCustomerAndStaffDTO;
import com.interchange.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface StaffService {
    List<User> getStaffs();
    ResponseEntity<?> addStaff(AddCustomerAndStaffDTO addCustomerAndStaffDTO);

    ResponseEntity<?> updateStaff(String userId, UpdateCustomerAndStaffDTO updateCustomerAndStaffDTO);
}
