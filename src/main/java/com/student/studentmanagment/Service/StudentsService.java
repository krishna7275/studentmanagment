package com.student.studentmanagment.Service;

import com.student.studentmanagment.dto.StudentDTO;

public interface StudentsService {

    boolean existsByEmailIgnoreCase(String email);

    StudentDTO createStudent(StudentDTO studentDTO);
}
