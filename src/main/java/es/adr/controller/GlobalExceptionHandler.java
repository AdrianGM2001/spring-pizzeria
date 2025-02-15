package es.adr.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ResponseStatusException.class)
    public ModelAndView handleResponseStatusException(ResponseStatusException ex) {
        ModelAndView mav = new ModelAndView("error");
        mav.addObject("errorMessage", ex.getReason());
        mav.addObject("statusCode", ex.getStatusCode().value());
        return mav;
    }
}
