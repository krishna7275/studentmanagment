package com.student.studentmanagment.Service.imp;

import com.student.studentmanagment.Controller.StudentController;
import com.student.studentmanagment.Service.StudentsService;
import com.student.studentmanagment.dto.StudentDTO;
import com.student.studentmanagment.repository.StudentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
        return null;
    }
}
