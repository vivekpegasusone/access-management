package com.drishti.accessmanagement.service.action;

import static com.drishti.accessmanagement.utils.StringUtility.apendTimeInMillis;
import static java.util.Objects.nonNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.InvalidRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.action.ActionRepository;
import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.action.ActionTransformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;

@Service
class ActionServiceImpl implements ActionService {

  private static final Logger logger = LoggerFactory.getLogger(ActionServiceImpl.class);
  
  @Autowired
  private ActionRepository actionRepository;

  private Transformer<Action, ActionDto> actionTransformer = new ActionTransformer();

  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<ActionDto> findAll() {
    logger.info("Retrieving all action records.");
    List<Action> actions = actionRepository.findAll();
    return actionTransformer.transform(actions);
  }

  @Override
  public List<ActionDto> findActiveActions() {
    logger.info("Retrieving all active action records.");
    List<Action> actionList = actionRepository.findByActiveTrue();

    if (!actionList.isEmpty()) {
      return actionTransformer.transform(actionList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public List<ActionDto> findInActiveActions() {
    logger.info("Retrieving all inactive action records.");
    List<Action> actionList = actionRepository.findByActiveFalse();

    if (!actionList.isEmpty()) {
      return actionTransformer.transform(actionList);
    } else {
      return Collections.emptyList();
    }
  }

  @Override
  public ActionDto findActionById(Long id) throws RecordNotFoundException {
    logger.info("Retrieving action for id {}.", id);
    Optional<Action> optionalAction = actionRepository.findById(id);

    if (optionalAction.isPresent()) {
      logger.debug("Action record found for id {}.", id);
      return actionTransformer.transform(optionalAction.get());
    } else {
      logger.debug("Action record not found for id {}.", id);
      throw new RecordNotFoundException("No record exist for given action id " + id);
    }
  }

  @Override
  public ActionDto createAction(ActionDto actionDto) throws DuplicateRecordException {
    if (Objects.nonNull(actionDto)) {
      logger.info("Creating action record for action {}.", actionDto.getName());
      Optional<Action> optionalAction = actionRepository.findByName(actionDto.getName());
      
      if(optionalAction.isPresent()) {
        throw new DuplicateRecordException("Duplicate record. Action name already exists.");
      } else {
        Action action = actionTransformer.transform(actionDto);
        action = actionRepository.save(action);
        return actionTransformer.transform(action); 
      }     
    } else {
      logger.info("Can not create action. Action information not present.");
      throw new InvalidRecordException("Can not create action. Action information not present.");
    }
  }

  @Override
  public ActionDto updateAction(ActionDto actionDto) throws RecordNotFoundException, DuplicateRecordException {
    if (Objects.nonNull(actionDto) && nonNull(actionDto.getId())) {
      logger.info("Updating action record for id {}.", actionDto.getId());
      
      Optional<Action> optionalAction = actionRepository.findById(actionDto.getId());
      if (optionalAction.isPresent()) {
        logger.debug("Action record found for id {}.", actionDto.getId());  
        Action action = optionalAction.get();
        
        if (action.getName().equalsIgnoreCase(actionDto.getName())) {
          throw new DuplicateRecordException("Duplicate action name. Action name already exists.");
        } else {
          action.setName(actionDto.getName());
          action.setDescription(actionDto.getDescription());
          action.setApplication(applicationTransformer.transform(actionDto.getApplicationDto()));

          actionRepository.save(action);
          return actionTransformer.transform(action);
        }
      } else {
        logger.debug("Can not update action. No record not found for action id {}.", actionDto.getId());
        throw new RecordNotFoundException("No record exist for given action id " + actionDto.getId());        
      }           
    } else {
      logger.info("Can not update action. Action id is empty.");
      throw new InvalidRecordException("Can not update action. Action id is empty.");
    }
  }

  @Override
  public void deleteActionById(Long id) throws RecordNotFoundException {
    logger.info("Archiving action record for id {}.", id);    
    Optional<Action> optionalAction = actionRepository.findById(id);  
    
    if (optionalAction.isPresent()) {
      logger.debug("Action record found for id {}.", id);  
      Action action = optionalAction.get(); 
      action.setActive(false);
      action.setName(apendTimeInMillis(action.getName()));
      // set all permission/mapping to false
      actionRepository.save(action);      
    } else {
      logger.debug("Can not archive action. No record found for action id {}.", id);
      throw new RecordNotFoundException("No record exist for given action id " + id);
    }      
  }

  @Override
  public List<ActionDto> findActionsByApplicationId(Long applicationId) {
    logger.info("Retrieving actions for application id {}.", applicationId);
    List<Action> actions = actionRepository.findActionsByApplicationId(applicationId);
    return actionTransformer.transform(actions);
  }

}
