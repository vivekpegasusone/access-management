package com.drishti.accessmanagement.dao.role;

import com.drishti.accessmanagement.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  List<Role> findByActiveTrue();

  List<Role> findByActiveFalse();

  List<Role> findByNameStartingWith(String prefix);

  List<Role> findByNameEndingWith(String suffix);

  List<Role> findByNameContaining(String infix);
}
