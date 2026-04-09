package com.student.studentmanagment.repository;

import com.student.studentmanagment.Model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users,Long> {
    Boolean existsByUsername(String username);

}
