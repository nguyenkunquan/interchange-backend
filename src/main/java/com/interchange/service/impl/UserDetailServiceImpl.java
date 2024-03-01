package com.interchange.service.impl;

import com.interchange.converter.UserConverter;
import com.interchange.dto.ChangePasswordDTO;
import com.interchange.dto.ForgetPasswordDTO;
import com.interchange.dto.RegisterDTO;
import com.interchange.entities.Role;
import com.interchange.entities.User;
import com.interchange.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDetailServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserIdOrEmailOrPhoneNumber(username, username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User can't found"));

        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        grantList.add(authority);

        return org.springframework.security.core.userdetails.
                User.builder()
                .username(user.getUserId()).
                password(user.getPassword())
                .authorities(grantList)
                .build();
    }

}
