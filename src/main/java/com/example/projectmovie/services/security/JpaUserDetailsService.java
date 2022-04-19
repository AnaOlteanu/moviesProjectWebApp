package com.example.projectmovie.services.security;

import com.example.projectmovie.domain.security.Authority;
import com.example.projectmovie.domain.security.User;
import com.example.projectmovie.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user;

        Optional<User> userOptional = userRepository.findByUsername(username);

        if(userOptional.isPresent()){
            user = userOptional.get();
            log.info("userOptional {} ", userOptional.get());
        } else{
            log.info("userOptional does not exist");
            throw new UsernameNotFoundException("Username: " + username);
        }

        log.info("User is {} pass {}", user.getUsername(), user.getPassword());

        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getEnabled(), user.getAccountNotExpired(),
                user.getCredentialsNotExpired(), user.getAccountNotLocked(),
                getAuthorities(user.getAuthorities()));
    }

    private Collection<? extends GrantedAuthority> getAuthorities(Set<Authority> authorities) {
        if(authorities == null){
            return new HashSet<>();
        } else if(authorities.size() == 0) {
            return new HashSet<>();
        } else {
            log.info("Authority {} ", authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet()));
            return authorities.stream()
                    .map(Authority::getRole)
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toSet());
        }
    }


}
