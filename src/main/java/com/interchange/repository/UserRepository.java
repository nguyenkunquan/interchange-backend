package com.interchange.repository;

import com.interchange.entities.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User>findByUserId(String username);

    Boolean existsByEmail(String email);
    Boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByUserIdOrEmailOrPhoneNumber(String userId, String phoneNumber, String email);
    boolean existsByUserId(String username);

    @Modifying
    @Transactional
    @Query("update User u set u.firstName= ?1, u.lastName= ?2, u.phoneNumber = ?3, " +
            "u.birthDate= ?4, u.province = ?5, u.district = ?6, " +
            "u.ward = ?7, u.streetAddress = ?8 where u.userId=?9")
    void setUserInfoById(String firstName, String lastName, String phoneNumber, String birthDate,
                         String province, String district, String ward, String streetAddress, String userID);
}
