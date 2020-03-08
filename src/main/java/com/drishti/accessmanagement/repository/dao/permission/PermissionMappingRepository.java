package com.drishti.accessmanagement.repository.dao.permission;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.permission.PermissionMapping;

@Repository
public interface PermissionMappingRepository extends JpaRepository<PermissionMapping, Long> {

}
