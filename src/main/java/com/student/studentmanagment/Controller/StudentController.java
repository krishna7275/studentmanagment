package com.student.studentmanagment.Controller;

import com.student.studentmanagment.Service.StudentsService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.dto.StudentDTO;
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
@RequestMapping("/students")
public class StudentController {
    private static final Logger log = LoggerFactory.getLogger(StudentController.class); // use to create lof file to fined error

    private final StudentsService studentsService;

    public StudentController(StudentsService studentsService) {
        this.studentsService = studentsService;
    }

    @GetMapping("/new")
    public String showCreateStudent(Model model) {
        log.info("Get /new -showing create students page");
        model.addAttribute("studentDto",new StudentDTO());

        return "add-student";
    }

    @GetMapping("/list")
    public String listStudent(){
        log.info("Get /list -showing list students page");
        return "students";
    }

    @PostMapping("/save")
    public String createStudent(@Valid @ModelAttribute("studentDto") StudentDTO studentDTO,
                                BindingResult bindingResult,//->“It holds validation results and errors.”
                                Model model, RedirectAttributes redirectAttributes){
        log.info("Post /save - create student request received");

        if (bindingResult.hasErrors()){
            return "add-student";
        }

        if (studentsService.existsByEmailIgnoreCase(studentDTO.getEmail())){
            log.error("Post /Students - Email should be unique");

            bindingResult.rejectValue("email",null,"Email should be unique");

            return "add-student";
        }

        return "";
    }
}
