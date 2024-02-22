package com.interchange.repository;

import com.interchange.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User>findByUserId(String username);

    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByUserIdOrEmailOrPhoneNumber(String userId, String phoneNumber, String email);
    boolean existsByUserId(String username);

}
