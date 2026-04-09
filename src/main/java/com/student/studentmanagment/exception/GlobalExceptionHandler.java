package com.student.studentmanagment.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CsrfException.class)
    public String csrfExceptionHandler(RedirectAttributes redirectAttributes){
        redirectAttributes.addFlashAttribute("message","Session expired,Please login again");
    return"redirect/login";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String genericExceptionHandler(){
        return "500";
    }
}
