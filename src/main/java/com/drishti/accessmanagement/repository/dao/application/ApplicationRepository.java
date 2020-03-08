package com.drishti.accessmanagement.repository.dao.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.application.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  Optional<Application> findByName(String name);

  List<Application> findByActiveTrue();

  List<Application> findByActiveFalse();
}

