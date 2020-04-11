package com.drishti.accessmanagement.repository.dao.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.action.Action;
import com.drishti.accessmanagement.repository.entity.permission.Permission;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

  Optional<Permission> findByName(String name);
  
  List<Permission> findByActiveTrue();

  List<Permission> findByActiveFalse();

  @Query("from Permission p "
      + "inner join fetch p.action a "
      + "inner join fetch p.resource r "
      + "inner join fetch r.application app "
      + "where a.application.id = r.application.id and app.active = true")
  List<Permission> findPermissionsByApplicationId(Long applicationId);
}
