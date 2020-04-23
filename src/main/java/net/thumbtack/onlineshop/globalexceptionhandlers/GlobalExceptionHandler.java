package net.thumbtack.onlineshop.globalexceptionhandlers;

import net.thumbtack.onlineshop.config.AppProperties;
import net.thumbtack.onlineshop.dto.response.FailedDtoResponse;
import net.thumbtack.onlineshop.dto.response.FailedItemDtoResponse;
import net.thumbtack.onlineshop.exception.AppErrorCode;
import net.thumbtack.onlineshop.exception.AppException;
import net.thumbtack.onlineshop.exception.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.util.Collections.singletonList;
import static net.thumbtack.onlineshop.exception.AppErrorCode.*;
import static net.thumbtack.onlineshop.exception.AppErrorCode.WRONG_URL;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public FailedDtoResponse fieldValidationHandler(MethodArgumentNotValidException ex) {
        List<FailedItemDtoResponse> failureResponseItems = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()) {
            AppErrorCode errorCode = AppProperties.getAppErrorCode(error.getField());
            failureResponseItems.add(new FailedItemDtoResponse(errorCode, errorCode.getField(), error.getDefaultMessage()));
        }
        return new FailedDtoResponse(failureResponseItems);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public FailedDtoResponse serviceExceptionHandler(ServiceException ex) {
        return new FailedDtoResponse(singletonList(new FailedItemDtoResponse(ex.getErrorCode(), ex.getField(), ex.getMessage())));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(AppException.class)
    @ResponseBody
    public FailedDtoResponse serviceExceptionHandler(AppException ex) {
        List<FailedItemDtoResponse> failureResponseItems = new ArrayList<>();
        ex.getServiceExceptions().forEach(exItem -> failureResponseItems.add(new FailedItemDtoResponse(exItem.getErrorCode(), exItem.getField(), exItem.getMessage())));
        return new FailedDtoResponse(failureResponseItems);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    public FailedDtoResponse httpMessageNotReadableExceptionHandler(HttpMessageNotReadableException ex) {
        List<FailedItemDtoResponse> failureResponseItems = new ArrayList<>();
        if (ex.getCause() instanceof IOException) {
            failureResponseItems.add(new FailedItemDtoResponse(JSON_PARSE_EXCEPTION, JSON_PARSE_EXCEPTION.getField(), JSON_PARSE_EXCEPTION.getErrorString()));
        } else {
            failureResponseItems.add(new FailedItemDtoResponse(NULL_REQUEST, NULL_REQUEST.getField(), NULL_REQUEST.getErrorString()));
        }
        return new FailedDtoResponse(failureResponseItems);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public FailedDtoResponse methodArgumentTypeMismatchExceptionHandler(MethodArgumentTypeMismatchException ex) {
        return new FailedDtoResponse(singletonList(new FailedItemDtoResponse(WRONG_REQUEST_PARAM, WRONG_REQUEST_PARAM.getField(),
                String.format(WRONG_REQUEST_PARAM.getErrorString(), ex.getName(), ex.getValue()))));
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    @ResponseBody
    public FailedDtoResponse httpMediaTypeNotSupportedExceptionHandler(HttpMediaTypeNotSupportedException ex) {
        return new FailedDtoResponse(singletonList(new FailedItemDtoResponse(WRONG_MEDIA_TYPE, WRONG_MEDIA_TYPE.getField(),
                String.format(WRONG_MEDIA_TYPE.getErrorString(), ex.getContentType()))));
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseBody
    public FailedDtoResponse noHandlerFoundExceptionHandler(NoHandlerFoundException ex) {
        return new FailedDtoResponse(singletonList(new FailedItemDtoResponse(WRONG_URL, WRONG_URL.getField(),
                String.format(WRONG_URL.getErrorString(), ex.getRequestURL()))));
    }
}
