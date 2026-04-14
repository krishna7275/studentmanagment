package com.student.studentmanagment.Service;

import com.student.studentmanagment.dto.StudentDTO;
import org.springframework.data.domain.Page;

public interface StudentsService {

    boolean existsByEmailIgnoreCase(String email);

    StudentDTO createStudent(StudentDTO studentDTO);

    Page<StudentDTO> getStudents(int page, int size);
}
