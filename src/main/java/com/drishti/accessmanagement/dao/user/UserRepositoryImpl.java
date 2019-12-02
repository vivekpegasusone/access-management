package com.drishti.accessmanagement.dao.user;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
class UserRepositoryImpl implements UserRepositoryCustom{

  @PersistenceContext
  private EntityManager entityManager;
}
