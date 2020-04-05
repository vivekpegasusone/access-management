package com.drishti.accessmanagement.repository.dao.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.resource.Resource;

@Repository
public interface ResourceRepository extends JpaRepository<Resource, Long> {

  Optional<Resource> findByName(String name);

  List<Resource> findByActiveTrue();

  List<Resource> findByActiveFalse();
  
  @Query("from Resource r inner join fetch r.application a where a.id = :applicationId and r.active = true")
  List<Resource> findResourcesByApplicationId(Long applicationId); 
}
