package com.drishti.accessmanagement.repository.dao.action;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.action.Action;

@Repository
public interface ActionRepository extends JpaRepository<Action, Long> {

  Optional<Action> findByName(String name);

  List<Action> findByActiveTrue();

  List<Action> findByActiveFalse();

  @Query("from Action ac inner join fetch ac.application a where a.id = :applicationId and ac.active = true")
  List<Action> findActionsByApplicationId(Long applicationId);
}
