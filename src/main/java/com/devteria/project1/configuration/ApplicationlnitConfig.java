package com.devteria.project1.configuration;

import com.devteria.project1.entity.User;
import com.devteria.project1.enums.Role;
import com.devteria.project1.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ApplicationlnitConfig {

      PasswordEncoder passwordEncoder ;
    @Bean
    ApplicationRunner applicationRunner(UserRepository userRepository) {
        return args -> {
             if(userRepository.findByUsername("admin").isEmpty()) {
                 Set<String> roles = new HashSet<>();
                 roles.add(Role.ADMIN.name());
                 User user = User.builder()
                         .username("admin")
                         .password(passwordEncoder.encode("admin"))
                         .roles(roles)
                         .build();

                 userRepository.save(user);
                 log.warn("admin user has been created with default password: admin, please change it");
             }

        };
    }
}
