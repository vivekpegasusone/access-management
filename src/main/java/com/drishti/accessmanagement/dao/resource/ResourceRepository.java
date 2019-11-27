package com.drishti.accessmanagement.dao.resource;

import com.drishti.accessmanagement.entity.resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {
}
