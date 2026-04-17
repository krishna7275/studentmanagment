package com.student.studentmanagment.repository;

import com.student.studentmanagment.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;

interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {
}
