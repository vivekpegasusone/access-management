package com.drishti.accessmanagement.dao.role;

import com.drishti.accessmanagement.entity.role.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long>, RoleRepositoryCustom{
}
