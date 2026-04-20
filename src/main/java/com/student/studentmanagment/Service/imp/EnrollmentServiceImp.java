package com.student.studentmanagment.Service.imp;

import com.student.studentmanagment.Model.Courses;
import com.student.studentmanagment.Model.Enrollment;
import com.student.studentmanagment.Model.Students;
import com.student.studentmanagment.Service.EnrollmentService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.dto.EnrollmentDTO;
import com.student.studentmanagment.dto.EnrollmentSummaryDTO;
import com.student.studentmanagment.repository.CourseRepository;
import com.student.studentmanagment.repository.EnrollmentRepository;
import com.student.studentmanagment.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImp implements EnrollmentService {

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class); // use to create lof file to fined error

    private final EnrollmentRepository enrollmentRepository;
    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper mapper;

    public EnrollmentServiceImp(EnrollmentRepository enrollmentRepository, StudentRepository studentRepository, CourseRepository courseRepository,ModelMapper mapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.studentRepository = studentRepository;
        this.courseRepository = courseRepository;
        this.mapper = mapper;
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

    @Override
    public Page<EnrollmentSummaryDTO> getEnrolledStudents(int page, int size) {
        log.info("list of enrolled Student from: {}",page);

        PageRequest pageRequest= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));

        //map() is used to transform elements in a stream
        return studentRepository.findEnrolledStudents(pageRequest)
                .map(student ->{
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName()+" "+student.getLastName());
                    dto.setEmail(student.getEmail());

                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO,BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    return dto;
                });
    }

    @Override
    public Optional<EnrollmentSummaryDTO> findEnrolledStudentsCourseDetails(Long studentId) {
        Students students = studentRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        return studentRepository.findEnrolledStudentsCourseDetails(studentId)
                .map(student ->{
                    EnrollmentSummaryDTO dto = new EnrollmentSummaryDTO();
                    dto.setStudentId(student.getId());
                    dto.setStudentName(student.getFirstName()+" "+student.getLastName());
                    dto.setEmail(student.getEmail());

                    dto.setCourseCount(student.getEnrollments().size());
                    BigDecimal totalFee = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse().getFee())
                            .filter(fee -> fee != null)
                            .reduce(BigDecimal.ZERO,BigDecimal::add);
                    dto.setTotalFee(totalFee);

                    List<CourseDTO> courseList = student.getEnrollments().stream()
                            .map(enrollment -> enrollment.getCourse())
                            .map(courses -> mapper.map(courses,CourseDTO.class))
                            .collect(Collectors.toList());

                    dto.setCourseList(courseList);
                    return dto;
                });
    }


}
