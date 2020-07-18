package com.drishti.accessmanagement.exception;

public class DuplicateRecordException extends Exception {

  private static final long serialVersionUID = -4825362390108586402L;

  public DuplicateRecordException(String message) {
    super(message);
  }

  public DuplicateRecordException(String message, Throwable cause) {
    super(message, cause);
  }  
}
