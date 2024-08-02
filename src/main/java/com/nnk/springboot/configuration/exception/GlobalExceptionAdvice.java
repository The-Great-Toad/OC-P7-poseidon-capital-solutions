package com.nnk.springboot.configuration.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

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
}
