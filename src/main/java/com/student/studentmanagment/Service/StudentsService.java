package com.student.studentmanagment.Service;

import com.student.studentmanagment.dto.StudentDTO;
import org.springframework.data.domain.Page;

public interface StudentsService {

    boolean existsByEmailIgnoreCase(String email);

    StudentDTO createStudent(StudentDTO studentDTO);

    Page<StudentDTO> getStudents(int page, int size);

    boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id);

    StudentDTO getStudentById(Long id);

    StudentDTO updateStudent(Long id, StudentDTO studentDTO);
}
