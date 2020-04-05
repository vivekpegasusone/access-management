package com.drishti.accessmanagement.service.action;

import java.util.List;

import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;

public interface ActionService {

  List<ActionDto> findAll();

  List<ActionDto> findActiveActions();

  List<ActionDto> findInActiveActions();

  ActionDto findActionById(Long id) throws RecordNotFoundException;

  ActionDto createAction(ActionDto actionDto);

  ActionDto updateAction(ActionDto actionDto) throws RecordNotFoundException;

  void deleteActionById(Long id) throws RecordNotFoundException;

  List<ActionDto> findActionsByApplicationId(Long applicationId);
}
