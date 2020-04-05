package com.drishti.accessmanagement.service.transformer.action;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;

import com.drishti.accessmanagement.dto.action.ActionDto;
import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;

public class ActionTransformer implements Transformer<Action, ActionDto> {

  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public ActionDto transform(Action action) {
    ApplicationDto appDto = applicationTransformer.transform(action.getApplication());

    ActionDto actionDto = new ActionDto(action.getId(), action.getName(), action.getDescription(), action.isActive());
    actionDto.setCreatedBy(action.getCreatedBy());
    actionDto.setCreatedOn(action.getCreatedOn());
    actionDto.setUpdatedBy(action.getUpdatedBy());
    actionDto.setUpdatedOn(action.getUpdatedOn());

    actionDto.setApplicationDto(appDto);

    return actionDto;
  }

  @Override
  public Action transform(ActionDto actionDto) {
    Action action = new Action.ActionBuilder(actionDto.getName()).setId(actionDto.getId())
        .setDescription(actionDto.getDescription()).setActive(actionDto.isActive()).build();

    Application app = applicationTransformer.transform(actionDto.getApplicationDto());
    action.setApplication(app);

    return action;
  }

  @Override
  public List<ActionDto> transform(List<Action> actions) {
    List<ActionDto> actionDtos;

    if (CollectionUtils.isEmpty(actions)) {
      actionDtos = Collections.emptyList();
    } else {
      actionDtos = actions.stream().map(r -> transform(r)).collect(Collectors.toList());
    }

    return actionDtos;
  }

}
