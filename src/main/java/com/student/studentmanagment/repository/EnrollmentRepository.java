package com.student.studentmanagment.repository;

import com.student.studentmanagment.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);
}
