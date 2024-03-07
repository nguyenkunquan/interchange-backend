package com.interchange.service.impl;

import com.interchange.converter.CustomerConverter;
import com.interchange.dto.ManageCustomerDTO.AddCustomerDTO;
import com.interchange.dto.ManageCustomerDTO.UpdateCustomerDTO;
import com.interchange.entities.Role;
import com.interchange.entities.User;
import com.interchange.repository.UserRepository;
import com.interchange.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;

    private final CustomerConverter customerConverter;
    private final PasswordEncoder passwordEncoder;
    public CustomerServiceImpl(UserRepository userRepository,
                               CustomerConverter customerConverter,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerConverter = customerConverter;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public List<User> getCustomers() {
        return userRepository.getAllByRole(Role.CUSTOMER);
    }
    @Override
    public ResponseEntity<?> addCustomer(AddCustomerDTO addCustomerDTO) {
        User user = customerConverter.toUser(addCustomerDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Register successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateCustomer(String userId, UpdateCustomerDTO updateCustomerDTO) {
        User user = userRepository.findFirstByUserId(userId);
        user = customerConverter.toUser(updateCustomerDTO, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Update successfully!", HttpStatus.OK);
    }
}
