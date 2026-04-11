package com.student.studentmanagment.Service.imp;

import com.student.studentmanagment.Model.Users;
import com.student.studentmanagment.repository.UsersRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceIpml implements UserDetailsService {

    private UsersRepository usersRepository;

    public UserServiceIpml (UsersRepository usersRepository){
        this.usersRepository=usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users users = usersRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Invalid username"));
        return User.withUsername(username)
                .password(users.getPassword())
                .disabled(!users.isActive())
                .build();
    }
}
