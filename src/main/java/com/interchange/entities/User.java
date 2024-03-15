package com.interchange.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;


@ToString
@Entity
@Table(name = "user")
// , uniqueConstraints = @UniqueConstraint(columnNames = "password")
@Data
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    @Length(max = 10, message = "The User ID must be less than 10 characters")
    @NotBlank(message = "The User ID can not null")
    private String userId;
    @Column(name = "password", nullable = false, length = 45)
    @NotBlank(message = "The password can not null")
    private String password;
    @Column(name = "first_name", nullable = false, length = 45)
    @NotBlank(message = "The first name can not null")
    private String firstName;
    @Column(name = "last_name", nullable = false, length = 45)
    @NotBlank(message = "The last name can not null")
    private String lastName;
    @Column(name = "birth_date", nullable = false)
    @NotBlank(message = "The birth date can not null")
    private String birthDate;
    @Column(name = "province", nullable = false, length = 45)
    @NotBlank(message = "The province can not null")
    private String province;
    @Column(name = "district", nullable = false, length = 45)
    @NotBlank(message = "The district can not null")
    private String district;
    @Column(name = "ward", nullable = false, length = 45)
    @NotBlank(message = "The ward can not null")
    private String ward;
    @Column(name = "street_address", nullable = false, length = 45)
    @NotBlank(message = "The Street Address can not null")
    private String streetAddress;
    @Column(name = "phone_number", nullable = false, length = 45, unique = true)
    @NotBlank(message = "The phone number can not null")
    @Pattern(regexp = "^\\+[1-9]\\d{1,14}$", message = "Invalid phone number")
    private String phoneNumber;
    @Column(name = "email", nullable = false, length = 45, unique = true)
    @NotBlank(message = "The email can not null")
    @Email(message = "Invalid email format")
    private String email;
    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String userId, String password, String firstName, String lastName, String birthDate, String province, String district, String ward, String streetAddress, String phoneNumber, String email, Role role) {
        this.userId = userId;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.province = province;
        this.district = district;
        this.ward = ward;
        this.streetAddress = streetAddress;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.role = role;
    }

    public User() {
    }

    public boolean isOver18() {
        if (birthDate == null || birthDate.isEmpty()) {
            return false;
        }
        try {
            LocalDate birthdateObj = LocalDate.parse(birthDate);

            LocalDate currentDate = LocalDate.now();
            Period age = Period.between(birthdateObj, currentDate);

            return age.getYears() >= 18;
        } catch (Exception ex) {
            return false;
        }
    }

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "staffId")
    private Set<Blog> blogs = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "customer")
    private Set<MainProject> mainProjects1 = new HashSet<>();

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true, mappedBy = "staff")
    private Set<MainProject> mainProjects2 = new HashSet<>();

}
