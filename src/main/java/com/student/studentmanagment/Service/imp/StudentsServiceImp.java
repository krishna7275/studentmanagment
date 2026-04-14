package com.student.studentmanagment.Service.imp;

import com.student.studentmanagment.Controller.StudentController;
import com.student.studentmanagment.Model.Courses;
import com.student.studentmanagment.Model.Students;
import com.student.studentmanagment.Service.StudentsService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.dto.StudentDTO;
import com.student.studentmanagment.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class StudentsServiceImp implements StudentsService {

    private static final Logger log = LoggerFactory.getLogger(StudentsServiceImp.class); // use to create lof file to fined error


    private final StudentRepository studentRepository;

    private final ModelMapper mapper;

    public StudentsServiceImp(StudentRepository studentRepository, ModelMapper mapper) {
        this.studentRepository = studentRepository;
        this.mapper = mapper;
    }


    @Override
    public boolean existsByEmailIgnoreCase(String email) {
        log.info("email from create student");
        return studentRepository.existsByEmailIgnoreCase(email);
    }

    @Override
    public StudentDTO createStudent(StudentDTO studentDTO) {
        log.info("saving student data");

        Students students =mapper.map(studentDTO, Students.class);
        Students saved = studentRepository.save(students);
        return mapper.map(saved,StudentDTO.class);
    }

    @Override
    public Page<StudentDTO> getStudents(int page, int size) {
        log.info("list of Student from: {}",page);
        PageRequest pageRequest= PageRequest.of(page,size, Sort.by(Sort.Direction.DESC,"id"));

        //map() is used to transform elements in a stream
        return studentRepository.findByActiveTrue(pageRequest)
                .map(student -> mapper.map(student, StudentDTO.class));
    }

    @Override
    public boolean existsByEmailIgnoreCaseAndIdNot(String email, Long id) {
        log.info("email from update page:{}, id: {}",email,id);
        return studentRepository.existsByEmailIgnoreCaseAndIdNot(email,id);
    }

    @Override
    public StudentDTO getStudentById(Long id) {
        Students students =studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Course Found"));

        return mapper.map(students,StudentDTO.class);
    }

    @Override
    public StudentDTO updateStudent(Long id, StudentDTO studentDTO) {
        Students students =studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No Student Found"));

        mapper.map(studentDTO,students);
        //-> mapper Internal call
        //-> courses.setCourseCode(courseDTO.getCourseDTO());

        Students updated =  studentRepository.save(students);
        return mapper.map(updated,StudentDTO.class);
    }
}
