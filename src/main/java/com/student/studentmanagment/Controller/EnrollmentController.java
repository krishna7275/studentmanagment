package com.student.studentmanagment.Controller;

import com.student.studentmanagment.Service.CourseService;
import com.student.studentmanagment.Service.StudentsService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.dto.EnrollmentDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class); // use to create lof file to fined error

    private final CourseService courseService;
    private final StudentsService studentsService;

    public EnrollmentController(CourseService courseService, StudentsService studentsService) {
        this.courseService = courseService;
        this.studentsService = studentsService;
    }

    @GetMapping("/showEnroll")
    public String showEnroll(Model model){
        log.info("Get /course/new - showing Enrollment page.");

        model.addAttribute("enrollmentDto",new EnrollmentDTO());
        model.addAttribute("courseList",courseService.getAllCourses());
        model.addAttribute("studentList",studentsService.getAllStudents());
        return "enroll-course";
    }
}
