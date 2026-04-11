package com.student.studentmanagment.Service;

import com.student.studentmanagment.dto.CourseDTO;

public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO);

    boolean existsByCode(String code);
}
