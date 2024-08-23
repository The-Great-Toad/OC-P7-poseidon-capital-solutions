package com.nnk.springboot.configuration.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.thymeleaf.exceptions.TemplateEngineException;
import org.thymeleaf.exceptions.TemplateInputException;

import java.sql.SQLException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice {

    private final String LOG_ID = "[GlobalExceptionAdvice]";

    @ExceptionHandler({ SQLException.class, DataAccessException.class })
    public String handleSqlException(final SQLException e) {
        log.error("{} - {}: {}", LOG_ID, e.getClass().getSimpleName(), e.getMessage());
        // todo pass on exception info
        return "error/databaseError";
    }

    // TODO : not working

//    @ExceptionHandler({TemplateEngineException.class, TemplateInputException.class})
//    public String handleTemplateInputException(final RuntimeException e, Model model) {
//        log.error("{} - {}: {}", LOG_ID, e.getClass().getSimpleName(), e.getMessage());
//
//        model.addAttribute("errorType", e.getClass().getSimpleName());
//        model.addAttribute("errorMsg", e.getMessage());
//
//        return "error/thymeleafError";
//    }

    @ExceptionHandler(Exception.class)
    public String handleTemplateInputException(final Exception e, Model model) {
        log.error("{} - {}: {}", LOG_ID, e.getClass().getSimpleName(), e.getMessage());

        model.addAttribute("errorType", e.getClass().getSimpleName());
        model.addAttribute("errorMsg", e.getMessage());

        return "error/thymeleafError";
    }
}
