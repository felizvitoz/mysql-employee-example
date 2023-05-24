package com.error.errorhandler;

import com.error.customerror.ApplicationException;
import com.error.customerror.BusinessException;
import com.error.response.CustomErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                               HttpHeaders headers, HttpStatus status, WebRequest request) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .errorCode(0)
                .message(ex.getMessage()).build();
        return new ResponseEntity(response, headers, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {ApplicationException.class})
    protected ResponseEntity<Object> handleApplicationExceptionError(RuntimeException ex, WebRequest request) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .errorCode(0)
                .message(ex.getMessage()).build();
        return new ResponseEntity(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<Object> handleBusinessExceptionError(RuntimeException ex, WebRequest request) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .errorCode(0)
                .message(ex.getMessage()).build();
        return new ResponseEntity(response, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleError(RuntimeException ex, WebRequest request) {
        CustomErrorResponse response = CustomErrorResponse.builder()
                .errorCode(0)
                .message(ex.getMessage()).build();
        return new ResponseEntity(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
