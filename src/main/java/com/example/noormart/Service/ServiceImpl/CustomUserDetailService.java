package com.example.noormart.Service.ServiceImpl;

import com.example.noormart.Model.LocalUser;
import com.example.noormart.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        LocalUser user=userRepo.findByEmail(username).orElseThrow(()->new UsernameNotFoundException("User Not found with this email."));
        return user;
    }

}