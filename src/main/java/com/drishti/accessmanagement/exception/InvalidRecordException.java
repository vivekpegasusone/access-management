package com.drishti.accessmanagement.exception;

public class InvalidRecordException extends RuntimeException {

  private static final long serialVersionUID = 8067810585431368712L;

  public InvalidRecordException(String message) {
    super(message);
  }

  public InvalidRecordException(String message, Throwable cause) {
    super(message, cause);
  }
}