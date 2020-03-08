package com.drishti.accessmanagement.controller.web.view;

import java.io.Serializable;

public class OptionView implements Serializable{

  private static final long serialVersionUID = -2769193477857019188L;

  private Long key;
  private String value;
  
  public OptionView(Long key, String value) {
    super();
    this.key = key;
    this.value = value;
  }

  public Long getKey() {
    return key;
  }

  public void setKey(Long key) {
    this.key = key;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return "OptionView [key=" + key + ", value=" + value + "]";
  }  
}
