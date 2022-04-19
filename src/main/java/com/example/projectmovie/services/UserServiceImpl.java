package com.example.projectmovie.services;

import com.example.projectmovie.domain.security.Authority;
import com.example.projectmovie.domain.security.User;
import com.example.projectmovie.repositories.AuthorityRepository;
import com.example.projectmovie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthorityRepository authorityRepository;
    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;

    @Override
    public User save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        Set<Authority> authoritySet = new HashSet<>();
        Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_GUEST").build());
        authoritySet.add(guestRole);
        user.setAuthorities(authoritySet);
        return userRepository.save(user);
    }
}
