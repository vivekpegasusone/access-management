package com.drishti.accessmanagement.exception;

import java.io.PrintWriter;
import java.io.StringWriter;

public class ErrorInfo {
  private String url;
  private String errorMessage;
  private String errorDetail;

  public ErrorInfo(String url, Exception exception) {
    StringWriter stringWriter = new StringWriter();
    exception.printStackTrace(new PrintWriter(stringWriter));
    this.url = url;
    this.errorMessage = exception.getMessage();
    this.errorDetail = stringWriter.toString();    
  }
  
  public ErrorInfo(String url, String errorMessage, String errorDetail) {
    this.url = url;
    this.errorMessage = errorMessage;
    this.errorDetail = errorDetail;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public String getErrorMessage() {
    return errorMessage;
  }

  public void setErrorMessage(String errorMessage) {
    this.errorMessage = errorMessage;
  }

  public String getErrorDetail() {
    return errorDetail;
  }

  public void setErrorDetail(String errorDetail) {
    this.errorDetail = errorDetail;
  }
}
