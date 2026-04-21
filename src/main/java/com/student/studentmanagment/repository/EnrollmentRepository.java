package com.student.studentmanagment.repository;

import com.student.studentmanagment.Model.Enrollment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface EnrollmentRepository extends JpaRepository<Enrollment,Long> {

    boolean existsByStudentIdAndCourseId(Long studentId, Long courseId);

    @Query("""
        select count (distinct e.student.id) from 
                Enrollment e 
                where e.enrolledDate between :startDate and :endDate      
        """)
    long countDistinctStudentByEnrolledDateBetween(@Param("startDate")LocalDateTime startDate,
                                                   @Param("endDate")LocalDateTime endDate);
}
