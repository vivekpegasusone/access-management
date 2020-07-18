package com.drishti.accessmanagement.service.role;

import static com.drishti.accessmanagement.utils.StringUtility.apendTimeInMillis;
import static java.util.Objects.nonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.drishti.accessmanagement.dto.application.ApplicationDto;
import com.drishti.accessmanagement.dto.role.RoleDto;
import com.drishti.accessmanagement.exception.DuplicateRecordException;
import com.drishti.accessmanagement.exception.InvalidRecordException;
import com.drishti.accessmanagement.exception.RecordNotFoundException;
import com.drishti.accessmanagement.repository.dao.role.RoleRepository;
import com.drishti.accessmanagement.repository.entity.application.Application;
import com.drishti.accessmanagement.repository.entity.role.Role;
import com.drishti.accessmanagement.service.transformer.Transformer;
import com.drishti.accessmanagement.service.transformer.application.ApplicationTransformer;
import com.drishti.accessmanagement.service.transformer.role.RoleTransformer;

@Service
class RoleServiceImpl implements RoleService {
  
  private static final Logger logger = LoggerFactory.getLogger(RoleServiceImpl.class);
  
  @Autowired
  private RoleRepository roleRepository;
    
  private Transformer<Role, RoleDto> roleTransformer = new RoleTransformer();
  
  private Transformer<Application, ApplicationDto> applicationTransformer = new ApplicationTransformer();

  @Override
  public List<RoleDto> findAll() {
    logger.info("Retrieving all role records.");
    List<Role> roles = roleRepository.findAll();
    return roleTransformer.transform(roles);
  }
  
  @Override
  public List<RoleDto> findActiveRoles() {
    logger.info("Retrieving all active role records.");
    List<Role> roleList = roleRepository.findByActiveTrue();

    if (!roleList.isEmpty()) {
      return roleTransformer.transform(roleList);
    } else {
      return new ArrayList<>();
    }
  }
  
  @Override
  public List<RoleDto> findInActiveRoles() {
    logger.info("Retrieving all inactive role records.");
    List<Role> roleList = roleRepository.findByActiveFalse();

    if (!roleList.isEmpty()) {
      return roleTransformer.transform(roleList);
    } else {
      return new ArrayList<>();
    }
  }

  @Override
  public RoleDto findRoleById(Long id) throws RecordNotFoundException {
    logger.info("Retrieving role for id {}.", id);
    Optional<Role> optionalRole = roleRepository.findById(id);

    if (optionalRole.isPresent()) {
      logger.debug("Role record found for id {}.", id);
      return roleTransformer.transform(optionalRole.get());
    } else {
      logger.debug("Role record not found for id {}.", id);
      throw new RecordNotFoundException("No record exist for given role id " + id);
    }
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public RoleDto createRole(RoleDto roleDto) throws DuplicateRecordException{
    if (Objects.nonNull(roleDto)) {
      logger.info("Creating role record for role {}.", roleDto.getName());
      Optional<Role> optionalRole = roleRepository.findByNameOrApplicationAndPermission(
          roleDto.getName(), roleDto.getApplicationDto().getId(), roleDto.getPermissionDto().getId());
      
      if (optionalRole.isPresent()) {
        Role role = optionalRole.get();
        if(role.getName().equalsIgnoreCase(roleDto.getName())) {
          throw new DuplicateRecordException("Duplicate record. Role name already exists.");
        } else {
          throw new DuplicateRecordException("Duplicate record. Role already exists with same application and permission.");
        }
      } else {
        Role role = roleTransformer.transform(roleDto);
        role = roleRepository.saveAndFlush(role);
        return roleTransformer.transform(role);
      }      
    } else {
      logger.info("Can not create role. Role information not present.");
      throw new InvalidRecordException("Can not create role. Role information not present.");
    }
  }

  @Override
  @Transactional(propagation= Propagation.REQUIRED)
  public RoleDto updateRole(RoleDto roleDto) throws RecordNotFoundException, DuplicateRecordException {
    if (Objects.nonNull(roleDto) && nonNull(roleDto.getId())) {
      logger.info("Updating role record for id {}.", roleDto.getId());
      Optional<Role> optionalRole = roleRepository.findById(roleDto.getId());

      if (optionalRole.isPresent()) {
        logger.debug("Role record found for id {}.", roleDto.getId());  
        Role role = optionalRole.get();
        
        if (role.getName().equalsIgnoreCase(roleDto.getName())) {
          throw new DuplicateRecordException("Duplicate role name. Role name already exists.");
        } else if (role.getApplication().getId() == roleDto.getApplicationDto().getId()
            && role.getPermission().getId() == roleDto.getPermissionDto().getId()) {
          throw new DuplicateRecordException("Duplicate permission. Role already exists with same application and permission.");
        } else {
          role.setName(roleDto.getName());
          role.setDescription(roleDto.getDescription());
          role.setApplication(applicationTransformer.transform(roleDto.getApplicationDto()));

          roleRepository.save(role);
          return roleTransformer.transform(role);
        }        
      } else {
        logger.debug("Can not update role. No record not found for role id {}.", roleDto.getId());
        throw new RecordNotFoundException("No record exist for given role id " + roleDto.getId());    
      }      
    } else {
      logger.info("Can not update role. Role id is empty.");
      throw new InvalidRecordException("Can not update role. Role id is empty.");
    }
  }

  @Override
  public void deleteRoleById(Long id) throws RecordNotFoundException {
    logger.info("Archiving role record for id {}.", id);
    Optional<Role> optionalRole = roleRepository.findById(id);    
    
    if (optionalRole.isPresent()) {
      logger.debug("Role record found for id {}.", id);  
      Role role = optionalRole.get();
      role.setActive(false);
      role.setName(apendTimeInMillis(role.getName()));
      role.setPermission(null);
      role.setApplication(null);
      // set all users, roles, actions, resources false
      roleRepository.save(role);   
    } else {
      logger.debug("Can not archive role. No record found for role id {}.", id);
      throw new RecordNotFoundException("No record exist for given role id " + id);
    }
  }

  @Override
  public List<RoleDto> findRolesByApplicationId(Long applicationId) {
    logger.info("Retrieving roles for application id {}.", applicationId);
    List<Role> roles = roleRepository.findRolesByApplicationId(applicationId);
    return roleTransformer.transform(roles);
  }    
}
