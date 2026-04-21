package com.student.studentmanagment.Controller;

import com.student.studentmanagment.Service.CourseService;
import com.student.studentmanagment.Service.EnrollmentService;
import com.student.studentmanagment.Service.StudentsService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.dto.EnrollmentDTO;
import com.student.studentmanagment.dto.EnrollmentSummaryDTO;
import com.student.studentmanagment.dto.StudentDTO;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

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
    public String enrollmentList(@RequestParam(defaultValue = "0")int page,
                                 @RequestParam(defaultValue = "3")int size,
                                 Model model,
                                 @RequestParam(value = "message",required = false) String message){
        log.info("Get /enrollmentList - list of enrolled students page");

        Page<EnrollmentSummaryDTO> students = enrollmentService.getEnrolledStudents(page,size);
        model.addAttribute("students",students);
        model.addAttribute("message",message);
        return "enrolled-students";
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

    @GetMapping("/getStudentEnrollmentDetails/{id}")
    public String getStudentEnrollmentDetails(@PathVariable Long id, Model model,
                                              @RequestParam(defaultValue = "enrollments") String source){
        Optional<EnrollmentSummaryDTO> enrollmentSummaryDTO
                =enrollmentService.findEnrolledStudentsCourseDetails(id);
        model.addAttribute("enrollmentSummaryDTO", enrollmentSummaryDTO);
        model.addAttribute("source", source);

        return "enrollment-details";
    }

}
