package com.example.projectmovie.loader;

import com.example.projectmovie.domain.security.Authority;
import com.example.projectmovie.domain.security.User;
import com.example.projectmovie.repositories.AuthorityRepository;
import com.example.projectmovie.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class DataLoader implements CommandLineRunner {

    private AuthorityRepository authorityRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;

    private void loadUserData(){
        if(userRepository.count() == 0){
            Authority adminRole = authorityRepository.save(Authority.builder().role("ROLE_ADMIN").build());
            Authority guestRole = authorityRepository.save(Authority.builder().role("ROLE_GUEST").build());

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .authority(adminRole)
                    .build();
            User guest = User.builder()
                    .username("guest")
                    .password(passwordEncoder.encode("guest"))
                    .authority(guestRole)
                    .build();

            userRepository.save(admin);
            userRepository.save(guest);
        }
    }

    @Override
    public void run(String... args) throws Exception {
        loadUserData();
    }
}
