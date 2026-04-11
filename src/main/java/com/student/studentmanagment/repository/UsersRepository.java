package com.student.studentmanagment.repository;

import com.student.studentmanagment.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Boolean existsByUsername(String username);

    Optional<Users> findByUsername(String username);
}
