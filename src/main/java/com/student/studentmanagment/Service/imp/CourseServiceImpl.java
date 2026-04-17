package com.student.studentmanagment.Service.imp;

import com.student.studentmanagment.Controller.CourseController;
import com.student.studentmanagment.Model.Courses;
import com.student.studentmanagment.Service.CourseService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.repository.CourseRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional //-> ye jo hai na vo ACID property kai jaise kaam kar ta hai
public class CourseServiceImpl implements CourseService {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class); // use to create lof file to fined error


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
        log.info("Creating course with code:{}",courseDTO.getCourseCode());
        Courses courses = mapper.map(courseDTO,Courses.class);//--> its is use to convert
        courseRepository.save(courses);

        return mapper.map(courses,CourseDTO.class);
    }

    @Override
    public boolean existsByCode(String code) {
        log.info("checking if code exists:{}",code);
        return courseRepository.existsByCourseCodeIgnoreCase(code);
    }

    @Override
    public boolean existsByCourseCodeIgnoreCaseAndIdNot(String code, Long id) {
        log.info("code from update page:{}, id: {}",code,id);
        return courseRepository.existsByCourseCodeIgnoreCaseAndIdNot(code,id);
    }

    @Override
    public Page<CourseDTO> getCourses(int page, int size) {
        log.info("list od course from: {}",page);
        PageRequest pageRequest= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));

        //map() is used to transform elements in a stream
        return courseRepository.findByActiveTrue(pageRequest)
                .map(courses -> mapper.map(courses,CourseDTO.class));
    }


    @Override
    public CourseDTO getCourseById(Long id) {
        Courses courses =courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Course Found"));

        return mapper.map(courses,CourseDTO.class);
    }

    @Override
    public CourseDTO updateCourse(Long id, CourseDTO courseDTO) {
        Courses courses =courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Course Found"));

        mapper.map(courseDTO,courses);
        //-> mapper Internal call
        //-> courses.setCourseCode(courseDTO.getCourseDTO());

        Courses updated =  courseRepository.save(courses);
        return mapper.map(updated,CourseDTO.class);
    }

    @Override
    public List<CourseDTO> getAllCourses() {
        return courseRepository.findByActiveTrue(Sort.by("courseName")).stream()
                .map(courses -> mapper.map(courses,CourseDTO.class))
                .collect(Collectors.toList());
    }
}
