package com.drishti.accessmanagement.utils;

import static com.drishti.accessmanagement.utils.Constants.UNDER_SCORE;

public class StringUtility {

  public static String apendTimeInMillis(String text) {
    StringBuilder sb = new StringBuilder(text);
    sb.append(UNDER_SCORE).append(System.currentTimeMillis());
    return sb.toString();
  }
}
