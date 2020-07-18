package com.drishti.accessmanagement.handler.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.drishti.accessmanagement.exception.ErrorInfo;
import com.drishti.accessmanagement.exception.InvalidRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

@ControllerAdvice
public class AccessManagementExceptionHandler {

  public static final String ERROR_INFO = "errorInfo";
  public static final String DEFAULT_ERROR_VIEW = "error";

  @ExceptionHandler(value = {RecordNotFoundException.class})
  @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "No record found.")
  public void noRecordHandler(HttpServletRequest request, Exception exception) throws Exception {
    ErrorInfo errorInfo = new ErrorInfo(request.getRequestURL().toString(), exception); 
    request.setAttribute(ERROR_INFO, errorInfo);
  }   
  
  @ExceptionHandler(value = {InvalidRecordException.class})
  @ResponseStatus(value = HttpStatus.PRECONDITION_FAILED, reason = "Precondition Failed.")
  public void invalidRecordHandler(HttpServletRequest request, Exception exception) throws Exception {
    ErrorInfo errorInfo = new ErrorInfo(request.getRequestURL().toString(), exception); 
    request.setAttribute(ERROR_INFO, errorInfo);
  } 
  
  @ExceptionHandler(value = Exception.class)
  @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error.")
  public void exceptionHandler(HttpServletRequest request, Exception exception) throws Exception {
    ErrorInfo errorInfo = new ErrorInfo(request.getRequestURL().toString(), exception); 
    request.setAttribute(ERROR_INFO, errorInfo);
  }    
}
