package com.drishti.accessmanagement.service.action;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.action.ActionRepository;
import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.action.ActionTransformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;

@Service
class ActionServiceImpl implements ActionService {

  @Autowired
  private ActionRepository actionRepository;

  private Transformer<Action, ActionDto> actionTransformer = new ActionTransformer();

  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<ActionDto> findAll() {
    List<Action> actions = actionRepository.findAll();
    return actionTransformer.transform(actions);
  }

  @Override
  public List<ActionDto> findActiveActions() {
    List<Action> actionList = actionRepository.findByActiveTrue();

    if (!actionList.isEmpty()) {
      return actionTransformer.transform(actionList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public List<ActionDto> findInActiveActions() {
    List<Action> actionList = actionRepository.findByActiveFalse();

    if (!actionList.isEmpty()) {
      return actionTransformer.transform(actionList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public ActionDto findActionById(Long id) throws RecordNotFoundException {
    Optional<Action> optionalAction = actionRepository.findById(id);

    if (optionalAction.isPresent()) {
      return actionTransformer.transform(optionalAction.get());
    } else {
      throw new RecordNotFoundException("No record exist for given action id " + id);
    }
  }

  @Override
  public ActionDto createAction(ActionDto actionDto) {
    Action action = actionTransformer.transform(actionDto);
    action = actionRepository.save(action);
    return actionTransformer.transform(action);
  }

  @Override
  public ActionDto updateAction(ActionDto actionDto) throws RecordNotFoundException {
    Action action = actionRepository.findById(actionDto.getId()).get();
    action.setName(actionDto.getName());
    action.setDescription(actionDto.getDescription());
    action.setApplication(applicationTransformer.transform(actionDto.getApplicationDto()));
     
    actionRepository.save(action);    
    return actionTransformer.transform(action);
  }

  @Override
  public void deleteActionById(Long id) throws RecordNotFoundException {
    Action action = actionRepository.findById(id).get();
    action.setActive(false);
    // set all permission/mapping to false
    actionRepository.save(action);
  }

  @Override
  public List<ActionDto> findActionsByApplicationId(Long applicationId) {
    List<Action> actions = actionRepository.findActionsByApplicationId(applicationId);
    return actionTransformer.transform(actions);
  }

}
