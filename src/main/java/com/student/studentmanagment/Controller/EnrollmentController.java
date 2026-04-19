package com.student.studentmanagment.Controller;

import com.student.studentmanagment.Service.CourseService;
import com.student.studentmanagment.Service.EnrollmentService;
import com.student.studentmanagment.Service.StudentsService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.dto.EnrollmentDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private static final Logger log = LoggerFactory.getLogger(CourseController.class); // use to create lof file to fined error

    private final CourseService courseService;
    private final StudentsService studentsService;
    private final EnrollmentService enrollmentService;

    public EnrollmentController(CourseService courseService, StudentsService studentsService,EnrollmentService enrollmentService) {
        this.courseService = courseService;
        this.studentsService = studentsService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/showEnroll")
    public String showEnroll(Model model){
        log.info("Get /enrollments/showEnroll - showing Enrollment page.");

        model.addAttribute("enrollmentDto",new EnrollmentDTO());
        model.addAttribute("courseList",courseService.getAllCourses());
        model.addAttribute("studentList",studentsService.getAllStudents());
        return "enroll-course";
    }

    @GetMapping("/enrollmentList")
    public String enrollmentList(Model model){
        log.info("Get /enrollments/enrollmentList - showing Enrollment page.");
        return "enroll-students";
    }

    @PostMapping("/enrollCourse")
    public String enrollCourse(@Valid @ModelAttribute("enrollmentDto") EnrollmentDTO enrollmentDto,
                               BindingResult bindingResult,//->“It holds validation results and errors.”
                               Model model, RedirectAttributes redirectAttributes){

        log.info("Post /enrollments/enrollCourse - enrollment request received.");

        if (bindingResult.hasErrors()){
            model.addAttribute("courseList",courseService.getAllCourses());
            model.addAttribute("studentList",studentsService.getAllStudents());
            return "enroll-course";
        }

        enrollmentService.enrollStudentToCourses(enrollmentDto);

        redirectAttributes.addAttribute("message","enrollment successfully");

        log.info("Post /enrollments/enrollCourse - enrollment successfully.");

        return "redirect:/enrollments/enrollmentList";
    }
}
