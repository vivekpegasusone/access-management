package com.drishti.accessmanagement.exception;

public class RecordNotFoundException extends Exception {

  private static final long serialVersionUID = -730280824186118755L;

  public RecordNotFoundException(String message) {
    super(message);
  }

  public RecordNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }
}