package com.student.studentmanagment.Service.imp;

import com.student.studentmanagment.Model.Courses;
import com.student.studentmanagment.Service.CourseService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Transactional //-> ye jo hai na vo ACID property kai jaise kaam kar ta hai
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

//    ModelMapper -> ye jo jarr hai na bo Entity ko DTO mai kar ta hai and vise versa
//    hamlog direct entity ko expose nahi kar tai hai islye DTO use kar tai hai

    public CourseServiceImpl(CourseRepository courseRepository,ModelMapper mapper) {
        this.courseRepository = courseRepository;
        this.mapper =  mapper;
    }

    @Override
    public CourseDTO createCourse(CourseDTO courseDTO) {
        Courses courses = mapper.map(courseDTO,Courses.class);//--> its is use to convert
        courseRepository.save(courses);

        return mapper.map(courses,CourseDTO.class);
    }

    @Override
    public boolean existsByCode(String code) {
        return courseRepository.existsByCodeIgnoreCase(code);
    }
}
