package com.drishti.accessmanagement.service.role;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.role.RoleRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;
import com.drishti.accessmanagement.service.transformer.role.RoleTransformer;

@Service
class RoleServiceImpl implements RoleService {

  @Autowired
  private RoleRepository roleRepository;
  
  private Transformer<Role, RoleDto> roleTransformer = new RoleTransformer();
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<RoleDto> findAll() {
    List<Role> roles = roleRepository.findAll();
    return roleTransformer.transform(roles);
  }
  
  @Override
  public List<RoleDto> findActiveRoles() {
    List<Role> roleList = roleRepository.findByActiveTrue();

    if (!roleList.isEmpty()) {
      return roleTransformer.transform(roleList);
    } else {
      return new ArrayList<>();
    }
  }
  
  @Override
  public List<RoleDto> findInActiveRoles() {
    List<Role> roleList = roleRepository.findByActiveFalse();

    if (!roleList.isEmpty()) {
      return roleTransformer.transform(roleList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public RoleDto findRoleById(Long id) {
    Optional<Role> optionalRole = roleRepository.findById(id);

    if (optionalRole.isPresent()) {
      return roleTransformer.transform(optionalRole.get());
    } else {
      throw new RecordNotFoundException("No record exist for given role id " + id);
    }
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public RoleDto createRole(RoleDto roleDto) {
    Role role = roleTransformer.transform(roleDto);
    role = roleRepository.save(role);
    return roleTransformer.transform(role);
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public RoleDto updateRole(RoleDto roleDto) {
    Role role = roleRepository.findById(roleDto.getId()).get();
    role.setName(roleDto.getName());
    role.setDescription(roleDto.getDescription());
    role.setApplication(applicationTransformer.transform(roleDto.getApplicationDto()));
     
    roleRepository.saveAndFlush(role);    
    return roleTransformer.transform(role);
  }

  @Override
  public void deleteRoleById(Long id) {
    Role role = roleRepository.findById(id).get();
    role.setActive(false);
    // set all users, roles, actions, resources false
    roleRepository.save(role);    
  }

  @Override
  public List<RoleDto> findRolesByApplicationId(Long applicationId) {
    List<Role> roles = roleRepository.findByRolesByApplicationId(applicationId);
    return roleTransformer.transform(roles);
  }
    
}
