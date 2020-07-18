package com.drishti.accessmanagement.service.transformer.action;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;

public class ActionTransformer implements Transformer<Action, ActionDto> {

  private static final Logger logger = LoggerFactory.getLogger(ActionTransformer.class);
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public ActionDto transform(Action action) {
    logger.info("Transforming action entity to action dto.");
    ActionDto actionDto = null;
    if (Objects.nonNull(action)) {
      actionDto = new ActionDto(action.getId(), action.getName(), action.getDescription(), action.isActive());
      actionDto.setCreatedBy(action.getCreatedBy());
      actionDto.setCreatedOn(action.getCreatedOn());
      actionDto.setUpdatedBy(action.getUpdatedBy());
      actionDto.setUpdatedOn(action.getUpdatedOn());

      ApplicationDto appDto = applicationTransformer.transform(action.getApplication());

      actionDto.setApplicationDto(appDto);
    }
    return actionDto;
  }

  @Override
  public Action transform(ActionDto actionDto) {
    logger.info("Transforming action dto to action entity.");
    Action action = null;
    if (Objects.nonNull(actionDto)) {
      action = new Action.ActionBuilder(actionDto.getName()).setId(actionDto.getId())
          .setDescription(actionDto.getDescription()).setActive(actionDto.isActive()).build();

      Application app = applicationTransformer.transform(actionDto.getApplicationDto());
      action.setApplication(app);
    }

    return action;
  }

  @Override
  public List<ActionDto> transform(List<Action> actions) {
    logger.info("Transforming action entitys to action dtos.");
    List<ActionDto> actionDtos;

    if (CollectionUtils.isEmpty(actions)) {
      actionDtos = Collections.emptyList();
    } else {
      actionDtos = actions.stream().map(r -> transform(r)).collect(Collectors.toList());
    }

    return actionDtos;
  }

}
