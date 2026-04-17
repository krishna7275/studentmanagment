package com.student.studentmanagment.Service;

import com.student.studentmanagment.dto.CourseDTO;
import org.springframework.data.domain.Page;

import java.util.List;

public interface CourseService {
    CourseDTO createCourse(CourseDTO courseDTO);

    boolean existsByCode(String code);

    boolean existsByCourseCodeIgnoreCaseAndIdNot(String code,Long id);

    Page<CourseDTO> getCourses(int page, int size);

    CourseDTO getCourseById(Long id);

    CourseDTO updateCourse(Long id, CourseDTO courseDTO);

    List<CourseDTO> getAllCourses();
}
