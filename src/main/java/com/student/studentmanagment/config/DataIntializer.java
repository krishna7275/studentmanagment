package com.student.studentmanagment.config;

import com.student.studentmanagment.Model.Users;
import com.student.studentmanagment.repository.UsersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataIntializer {
    @Bean
    CommandLineRunner laodSampleData(UsersRepository usersRepository, PasswordEncoder passwordEncoder){
        return args -> {
            if (!usersRepository.existsByUsername("Admin"))
            {
                Users users = new Users();
                users.setUsername("Admin");
                users.setPassword(passwordEncoder.encode("admin@123"));
                users.setActive(true);
                usersRepository.save(users);
            }

        };
    }

}
