package com.student.studentmanagment.Service;

import com.student.studentmanagment.dto.EnrollmentDTO;
import com.student.studentmanagment.dto.EnrollmentSummaryDTO;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface EnrollmentService {

    void enrollStudentToCourses(EnrollmentDTO enrollmentDTO);

    Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size);

    Optional<EnrollmentSummaryDTO> findEnrolledStudentsCourseDetails(Long studentId);

    List<EnrollmentSummaryDTO> getRecentlyEnrolledStudents();
}
