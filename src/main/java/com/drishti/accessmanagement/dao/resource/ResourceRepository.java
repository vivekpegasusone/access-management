package com.drishti.accessmanagement.dao.resource;

import com.drishti.accessmanagement.entity.resource.Resource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

  Optional<Resource> findByName(String name);

  List<Resource> findByActiveTrue();

  List<Resource> findByActiveFalse();
}
