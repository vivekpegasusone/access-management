package com.drishti.accessmanagement.repository.dao.permission;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.permission.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

  Optional<Permission> findByName(String name);
  
  List<Permission> findByActiveTrue();

  List<Permission> findByActiveFalse();

  @Query("from Permission p "
      + "inner join fetch p.action a "
      + "inner join fetch p.resource r "
      + "inner join fetch r.application app "
      + "where a.application.id = r.application.id and app.active = true and a.application.id = :applicationId")
  List<Permission> findPermissionsByApplicationId(Long applicationId);

  @Query("from Permission p where p.name = :name or (p.resource.id = :resourceId and p.action.id = :actionId)")
  Optional<Permission> findByNameOrResourceAndAction(String name, Long resourceId, Long actionId);
}
