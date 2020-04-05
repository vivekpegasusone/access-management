package com.drishti.accessmanagement.repository.dao.role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.role.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  Optional<Role> findByName(String name);

  List<Role> findByActiveTrue();

  List<Role> findByActiveFalse();
  
  @Query("from Role r inner join fetch r.application a where a.id = :applicationId and r.active = true")
  List<Role> findRolesByApplicationId(Long applicationId); 
}
