package com.interchange.service;

//import com.interchange.dao.UserDAO;
import com.interchange.entities.User;
import com.interchange.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
//    private final UserDAO userDAO;
    private final PasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    public UserDetailServiceImpl(@Qualifier("bCryptPasswordEncoder") PasswordEncoder passwordEncoder, UserRepository userRepository) {
//        this.userDAO = userDAO;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserIdOrEmailOrPhoneNumber(username, username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User can't found"));

        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        grantList.add(authority);
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUserId(),
                user.getPassword(), grantList);
//        System.out.println(passwordEncoder.encode(user.getPassword()));
//        System.out.println(passwordEncoder.matches("minhbeo", user.getPassword()));
        return userDetails;
    }


}
