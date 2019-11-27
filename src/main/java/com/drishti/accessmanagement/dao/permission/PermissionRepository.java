package com.drishti.accessmanagement.dao.permission;

import com.drishti.accessmanagement.entity.permission.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
