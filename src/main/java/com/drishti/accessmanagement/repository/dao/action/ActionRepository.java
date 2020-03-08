package com.drishti.accessmanagement.repository.dao.action;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.action.Action;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

  Optional<Action> findByName(String name);

  List<Action> findByActiveTrue();

  List<Action> findByActiveFalse();
}
