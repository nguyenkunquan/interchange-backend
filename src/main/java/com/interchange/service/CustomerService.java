package com.interchange.service;

import com.interchange.dto.ManageCustomerDTO.AddCustomerDTO;
import com.interchange.dto.ManageCustomerDTO.UpdateCustomerDTO;
import com.interchange.entities.User;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    List<User> getCustomers();
    ResponseEntity<?> addCustomer(AddCustomerDTO addCustomerDTO);

    ResponseEntity<?> updateCustomer(String userId, UpdateCustomerDTO updateCustomerDTO);
}
