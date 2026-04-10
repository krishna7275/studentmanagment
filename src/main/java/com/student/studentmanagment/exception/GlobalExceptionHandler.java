package com.student.studentmanagment.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.csrf.CsrfException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class); // use to create lof file to fined error


    @ExceptionHandler(CsrfException.class)
    public String csrfExceptionHandler(CsrfException ex, RedirectAttributes redirectAttributes){
        log.warn("CSRf validation failed",ex.getMessage());
        redirectAttributes.addFlashAttribute("message","Session expired,Please login again");
    return"redirect/login";
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String genericExceptionHandler(Exception ex){
        log.error("CSRf validation failed",ex.getMessage());
        return "500";
    }
}
