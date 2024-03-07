package com.interchange.controller;

import com.interchange.dto.ManageCustomerDTO.AddCustomerDTO;
import com.interchange.dto.ManageCustomerDTO.UpdateCustomerDTO;
import com.interchange.repository.UserRepository;
import com.interchange.service.CustomerService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/manageCustomer")
public class ManageCustomerController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CustomerService customerService;
    @GetMapping("/showCustomerList")
    public ResponseEntity<?> getCustomers() {
        return ResponseEntity.ok(customerService.getCustomers());
    }
    @PostMapping("/addCustomer")
    public  ResponseEntity<?> addCustomer(@Valid @RequestBody AddCustomerDTO addCustomerDTO) {
        if (Boolean.TRUE.equals(userRepository.existsByUserId(addCustomerDTO.getUserId()))) {
            return new ResponseEntity<>("User ID had in the system", HttpStatus.CONFLICT);
        }
        if (Boolean.TRUE.equals(userRepository.existsByEmail(addCustomerDTO.getEmail()))) {
            return new ResponseEntity<>("Email had in the system", HttpStatus.CONFLICT);
        }
        if (Boolean.TRUE.equals(userRepository.existsByPhoneNumber(addCustomerDTO.getPhoneNumber()))) {
            return new ResponseEntity<>("Phone number had in the system", HttpStatus.CONFLICT);
        }
        if(!addCustomerDTO.isOver18()) {
            return new ResponseEntity<>("You must more than 18 years old", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(customerService.addCustomer(addCustomerDTO));
    }
    @PutMapping("/updateCustomer/{userId}")
    public  ResponseEntity<?> updateCustomer(@PathVariable("userId") String userId,
                                             @Valid @RequestBody UpdateCustomerDTO updateCustomerDTO) {
        if(!updateCustomerDTO.isOver18()) {
            return new ResponseEntity<>("You must more than 18 years old", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(customerService.updateCustomer(userId, updateCustomerDTO));
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}
