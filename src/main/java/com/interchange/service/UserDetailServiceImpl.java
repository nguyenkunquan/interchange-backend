package com.interchange.service;

import com.interchange.entities.User;
import com.interchange.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {


    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUserIdOrEmailOrPhoneNumber(username, username, username)
                .orElseThrow(() -> new UsernameNotFoundException("User can't found"));

        List<GrantedAuthority> grantList = new ArrayList<>();
        GrantedAuthority authority = new SimpleGrantedAuthority("ROLE_" + user.getRole());
        grantList.add(authority);

        return  new org.springframework.security.core.userdetails.User(user.getUserId(),
                user.getPassword(), grantList);
    }
//    @Transactional
//    public void updateInfoByID(User user) {
//         if(userRepository.existsByUserId(user.getUserId())) {
//             user.setFirstName(user.getFirstName());
//             user.setLastName(user.getLastName());
//             user.setPhoneNumber(user.getPhoneNumber());
//             user.setEmail(user.getEmail());
//             user.setBirthDate(user.getBirthDate());
//             user.setProvince(user.getBirthDate());
//         }
//    }


}
