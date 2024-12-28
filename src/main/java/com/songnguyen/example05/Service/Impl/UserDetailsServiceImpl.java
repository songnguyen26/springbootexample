package com.songnguyen.example05.Service.Impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.songnguyen.example05.Entity.User;
import com.songnguyen.example05.Repository.UserRepo;
import com.songnguyen.example05.config.UserInfoConfig;
import com.songnguyen.example05.exceptions.ResourceNotFoundException;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private UserRepo userRepo;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
        Optional<User> user=userRepo.findByEmail(username);
        return user.map(UserInfoConfig::new).orElseThrow(()->new ResourceNotFoundException("User", "email", username));
    }
}
