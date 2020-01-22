package com.drishti.accessmanagement.dao.application;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.entity.action.Action;
import com.drishti.accessmanagement.entity.application.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

  Optional<Action> findByName(String name);

  List<Action> findByActiveTrue();

  List<Action> findByActiveFalse();
}

