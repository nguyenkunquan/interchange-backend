package com.interchange.repository;

import com.interchange.entities.Role;
import com.interchange.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> getFirstByUserId(String userId);

    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByUserIdOrEmailOrPhoneNumber(String userId, String phoneNumber, String email);
    boolean existsByUserId(String username);

    List<User> getAllByRole(Role role);

    @Transactional
    @Modifying
    @Query("update User u set u.password= ?1 where u.userId= ?2")
    void setPasswordById(String password, String userId);
    User findFirstByUserId(String userId);
}