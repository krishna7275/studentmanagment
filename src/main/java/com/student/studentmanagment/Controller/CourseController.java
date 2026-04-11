package com.student.studentmanagment.Controller;

import com.student.studentmanagment.Service.CourseService;
import com.student.studentmanagment.dto.CourseDTO;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/course")
public class CourseController {
    private  CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }




    @GetMapping("/new")
    public String showCreateCourse(Model model){
        model.addAttribute("courseDto",new CourseDTO());
        return "add-course";
    }

    @GetMapping("/list")
    public String listCourse(){
        return "add-course";
    }

    @PostMapping("")
    public String createCourse(@Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                               BindingResult bindingResult,
                               Model model, RedirectAttributes redirectAttributes){

        if (bindingResult.hasErrors()){
            return "add-course";
        }

        if(courseService.existsByCode(courseDTO.getCourseCode())){
            bindingResult.rejectValue("courseCode","Code must be Unique");
            return "add-course";
        }

        courseService.createCourse(courseDTO);
        redirectAttributes.addAttribute("message","Course is created successfully");
    return "/course/list";
    }
}
