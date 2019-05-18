package net.thumbtack.onlineshop.exception;

import net.thumbtack.onlineshop.dto.response.AllErrorResponse;
import net.thumbtack.onlineshop.dto.response.ErrorResponse;
import net.thumbtack.onlineshop.service.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.util.Collections.singletonList;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public ResponseEntity<AllErrorResponse> ServiceExceptionHandler(ServiceException ex) {
        return new ResponseEntity<>(new AllErrorResponse(singletonList(new ErrorResponse(ex.getErrorCode(), ex.getField(), ex.getMessage()))), HttpStatus.BAD_REQUEST);
    }



}
