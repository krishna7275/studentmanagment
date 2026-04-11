package com.student.studentmanagment.repository;

import com.student.studentmanagment.Model.Courses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Courses,Long> {

    boolean existsByCodeIgnoreCase(String code);
}
