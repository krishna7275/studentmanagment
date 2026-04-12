package com.student.studentmanagment.Controller;

import com.student.studentmanagment.Service.CourseService;
import com.student.studentmanagment.dto.CourseDTO;
import com.student.studentmanagment.exception.GlobalExceptionHandler;
import jakarta.validation.Valid;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/course")
public class CourseController {
    private final CourseService courseService;

    private static final Logger log = LoggerFactory.getLogger(CourseController.class); // use to create lof file to fined error


    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/new")
    public String showCreateCourse(Model model){
        log.info("Get /course/new - showing create course page.");
        model.addAttribute("courseDto",new CourseDTO());
        return "add-course";
    }

    @GetMapping("/list")
    public String listCourse(@RequestParam(defaultValue = "0")int page,
                             @RequestParam(defaultValue = "5")int size,
                             Model model,
                             @RequestParam(value = "message",required = false) String message){
        log.info("Get /course/list - showing list page.");

        Page<CourseDTO> courses = courseService.getCourses(page,size);
        model.addAttribute("courses",courses);
        model.addAttribute("message",message);
        return "courses";
    }

        @PostMapping("")
        public String createCourse(@Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                                   BindingResult bindingResult,//->“It holds validation results and errors.”
                                   Model model, RedirectAttributes redirectAttributes){

            log.info("Post /course/new - create course request received.");

            if (bindingResult.hasErrors()){
                log.error("Post /course/new - Page is return due to validation error.");
                return "add-course";
            }

            if(courseService.existsByCode(courseDTO.getCourseCode())){
                log.error("Post /course/new - Code must be unique.");
                bindingResult.rejectValue("courseCode",null,"Code must be Unique");
                return "add-course";
            }


            courseService.createCourse(courseDTO);
            redirectAttributes.addAttribute("message","Course is created successfully");

            log.info("Post /course/new - create course successfully created.");

            return "redirect:/course/list";
        }

        @GetMapping("{id}")
        public String getCourseById(@PathVariable Long id, @NotNull Model model){
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("course",course);

        return "view-course";
        }

    @GetMapping("/{id}/edit")
    public String editCourse(@PathVariable Long id, Model model) {
        CourseDTO course = courseService.getCourseById(id);
        model.addAttribute("courseDto", course);

        return "edit-course";
    }

    @PostMapping("/{id}/update")
    public String updateCourse(@PathVariable Long id,@Valid @ModelAttribute("courseDto") CourseDTO courseDTO,
                               BindingResult bindingResult,//->“It holds validation results and errors.”
                               Model model, RedirectAttributes redirectAttributes){

        log.info("Post /{id}/update  - update course request received.{}",id);

        if (bindingResult.hasErrors()){
            log.error("Post /{id}/update - Page is return due to validation error.");
            return "edit-course";
        }

        if(courseService.existsByCourseCodeIgnoreCaseAndIdNot(courseDTO.getCourseCode(),id)){
            log.error("Post /{id}/update - Code must be unique.");
            bindingResult.rejectValue("courseCode",null,"Code must be Unique");
            return "edit-course";
        }


        courseService.updateCourse(id,courseDTO);
        redirectAttributes.addAttribute("message","Course is update successfully");

        log.info("Post /{id}/update - updated course successfully created.");

        return "redirect:/course/list";

    }
}
