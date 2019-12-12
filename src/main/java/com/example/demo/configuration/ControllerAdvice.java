package com.example.demo.configuration;

import com.fasterxml.jackson.core.JsonProcessingException;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.transaction.NotSupportedException;
import java.io.IOException;


@RestControllerAdvice
public class ControllerAdvice {

    final Logger logger = LoggerFactory.getLogger(ControllerAdvice.class);

    @ExceptionHandler(NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public Response handleNotFound(NotFoundException ex) {
        logger.error("NOT FOUND", ex);
        Response response = new Response();
        response.setStatus(HttpStatus.NOT_FOUND);
        response.setError(ex.getStackTrace());
        response.setReturnMessage(ex.getMessage());

        return response;
    }

    @ExceptionHandler({JsonProcessingException.class, IOException.class})
    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
    public Response handleJsonException(Exception ex) {
        logger.error("Internal Error", ex);
        Response response = new Response();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setError(ex.getStackTrace());
        response.setReturnMessage(ex.getMessage());

        return response;
    }

    @ExceptionHandler({NotSupportedException.class})
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public Response handleBadRequest(NotSupportedException ex) {
        logger.error("Bad Request", ex);
        Response response = new Response();
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        response.setError(ex.getStackTrace());
        response.setReturnMessage(ex.getMessage());

        return response;
    }


}
