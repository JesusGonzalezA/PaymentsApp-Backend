package com.paymentsapp.paymentsapp.security;

import com.paymentsapp.paymentsapp.infraestructure.IUserRepository;
import com.paymentsapp.paymentsapp.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if ( user == null ) {
            throw new UsernameNotFoundException("Username " + username + " not found.");
        }

        ArrayList<GrantedAuthority> roles= new ArrayList<>();
        roles.add(new SimpleGrantedAuthority("BASIC"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                roles
        );
    }
}
