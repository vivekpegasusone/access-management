package com.drishti.accessmanagement.utils;

import static java.util.Objects.isNull;

public class ValidationUtility {

  public static boolean notNull(Object object, String message) {
    if(isNull(object)) {
      throw new IllegalArgumentException(message);
    } else {
      return true;
    }
  }
}
