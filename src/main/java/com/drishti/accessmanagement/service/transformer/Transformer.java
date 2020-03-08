package com.drishti.accessmanagement.service.transformer;

import java.util.List;

import com.drishti.accessmanagement.dto.AuditableDto;
import com.drishti.accessmanagement.repository.entity.audit.Auditable;

public interface Transformer<T extends Auditable, S extends AuditableDto> {
  
  S transform(T t);
  
  T transform(S s);
  
  List<S> transform(List<T> t);  
}
