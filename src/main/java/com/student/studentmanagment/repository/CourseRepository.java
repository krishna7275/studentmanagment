package com.student.studentmanagment.repository;

import com.student.studentmanagment.Model.Courses;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseRepository extends JpaRepository<Courses,Long> {

    boolean existsByCourseCodeIgnoreCase(String code);

    boolean existsByCourseCodeIgnoreCaseAndIdNot(String code,Long id);

    Page<Courses> findByActiveTrue(Pageable pageable);
    //-> This Pageabele use to display the data pagination

    List<Courses> findByActiveTrue(Sort sort);
}
