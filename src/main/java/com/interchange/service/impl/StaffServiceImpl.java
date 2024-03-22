package com.interchange.service.impl;

import com.interchange.base.BaseResponse;
import com.interchange.converter.CustomerAndStaffConverter;
import com.interchange.dto.ManageCustomerAndStaffDTO.AddCustomerAndStaffDTO;
import com.interchange.dto.ManageCustomerAndStaffDTO.UpdateCustomerAndStaffDTO;
import com.interchange.entities.Role;
import com.interchange.entities.User;
import com.interchange.repository.UserRepository;
import com.interchange.service.StaffService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StaffServiceImpl extends BaseResponse implements StaffService {
    private final UserRepository userRepository;

    private final CustomerAndStaffConverter customerAndStaffConverter;
    private final PasswordEncoder passwordEncoder;
    public StaffServiceImpl(UserRepository userRepository,
                               CustomerAndStaffConverter customerAndStaffConverter,
                               PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.customerAndStaffConverter = customerAndStaffConverter;
        this.passwordEncoder = passwordEncoder;
    }
    @Override
    public ResponseEntity<?> getStaffs() {
        return getResponseEntity(userRepository.getAllByRole(Role.STAFF));
    }

    @Override
    public ResponseEntity<?> getStaffById(String userId) {
        return getResponseEntity(userRepository.getFirstByUserId(userId));
    }

    @Override
    public ResponseEntity<?> addStaff(AddCustomerAndStaffDTO addCustomerAndStaffDTO) {
        User user = customerAndStaffConverter.toUser(addCustomerAndStaffDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRole(Role.STAFF);
        userRepository.save(user);
        return new ResponseEntity<>("Register successfully", HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> updateStaff(String userId, UpdateCustomerAndStaffDTO updateCustomerAndStaffDTO) {
        User user = userRepository.findFirstByUserId(userId);
        user = customerAndStaffConverter.toUser(updateCustomerAndStaffDTO, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return new ResponseEntity<>("Update successfully!", HttpStatus.OK);
    }


}
