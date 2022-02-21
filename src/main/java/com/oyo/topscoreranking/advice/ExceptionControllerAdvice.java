package com.oyo.topscoreranking.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import com.oyo.topscoreranking.exception.BusinessException;

@ControllerAdvice
public class ExceptionControllerAdvice {

	@ExceptionHandler(BusinessException.class)
    public ResponseEntity<String> handleBuisnessException(BusinessException buisnessException)
    {

        return new ResponseEntity<String>(buisnessException.getErrorCode()+":"+buisnessException.getErrorMessage(),HttpStatus.BAD_REQUEST);
    }
}
