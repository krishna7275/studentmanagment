package com.student.studentmanagment.Service.imp;

import com.student.studentmanagment.Model.Courses;
import com.student.studentmanagment.Model.Enrollment;
import com.student.studentmanagment.Model.Students;
import com.student.studentmanagment.Service.EnrollmentService;
import com.student.studentmanagment.dto.EnrollmentDTO;
import com.student.studentmanagment.repository.CourseRepository;
import com.student.studentmanagment.repository.EnrollmentRepository;
import com.student.studentmanagment.repository.StudentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class); // use to create lof file to fined error

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;

    public EnrollmentServiceImp(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public void enrollStudentToCourses(EnrollmentDTO enrollmentDTO) {
        log.info("request form enrollStudentToCourses");

        Students students = studentRepository.findById(enrollmentDTO.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found "));

        for (Long courseId : enrollmentDTO.getCourseIds()){
            Courses course = courseRepository.findById(courseId)
                    .orElseThrow(() -> new RuntimeException("course not found"));

            if(enrollmentRepository.existsByStudentIdAndCourseId(enrollmentDTO.getStudentId(),courseId)){
                continue;
            }

            Enrollment enrollment = new Enrollment();
            enrollment.setStudent(students);
            enrollment.setCourse(course);

            enrollmentRepository.save(enrollment);

        }


    }
}
