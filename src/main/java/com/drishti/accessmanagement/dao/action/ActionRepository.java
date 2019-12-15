package com.drishti.accessmanagement.dao.action;

import com.drishti.accessmanagement.entity.action.Action;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

  Optional<Action> findByName(String name);

  List<Action> findByActiveTrue();

  List<Action> findByActiveFalse();
}
