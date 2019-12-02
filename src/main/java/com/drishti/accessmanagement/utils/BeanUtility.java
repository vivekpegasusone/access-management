package com.drishti.accessmanagement.utils;

import org.springframework.beans.BeanUtils;

import java.util.List;

import static com.drishti.accessmanagement.utils.ValidationUtility.notNull;

public class BeanUtility {

  public static <S,T> List copy(List<S> source, List<T> target, Class<T> clazz) {
    notNull(source, "Source object is null. Can not copy to target object.");
    notNull(target, "Target object is null. Can not copy from source object.");

    for (S s : source) {
      T t = BeanUtils.instantiateClass(clazz);
      BeanUtils.copyProperties(s, t);
      target.add(t);
    }
    return target;
  }
}
