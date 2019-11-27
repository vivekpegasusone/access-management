package com.drishti.accessmanagement.dao.permission;

import com.drishti.accessmanagement.entity.permission.PermissionMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionMappingRepository extends JpaRepository<PermissionMapping, Long> {
}
