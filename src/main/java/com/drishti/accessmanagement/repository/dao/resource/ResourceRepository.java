package com.drishti.accessmanagement.repository.dao.resource;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.resource.Resource;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

  Optional<Resource> findByName(String name);

  List<Resource> findByActiveTrue();

  List<Resource> findByActiveFalse();
}
