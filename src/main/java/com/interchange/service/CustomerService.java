package com.interchange.service;

import com.interchange.dto.ManageCustomerAndStaffDTO.AddCustomerAndStaffDTO;
import com.interchange.dto.ManageCustomerAndStaffDTO.UpdateCustomerAndStaffDTO;
import com.interchange.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<?> getCustomers();

    ResponseEntity<?> getCustomerById(String userId);
    ResponseEntity<?> addCustomer(AddCustomerAndStaffDTO addCustomerAndStaffDTO);

    ResponseEntity<?> updateCustomer(String userId, UpdateCustomerAndStaffDTO updateCustomerAndStaffDTO);
}
