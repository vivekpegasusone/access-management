package com.drishti.accessmanagement.dao.user;

import com.drishti.accessmanagement.entity.user.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
class UserRepositoryImpl implements UserRepositoryCustom{

  @PersistenceContext
  private EntityManager entityManager;

  @Override
  public List<User> getFirstNamesLike(String firstName) {
    Query query = entityManager.createNativeQuery("SELECT u.* FROM users as u WHERE u.firstName LIKE ?", User.class);
    query.setParameter(1, firstName + "%");

    return query.getResultList();
  }

  @Override
  public List<User> getAllActiveUsers() {
    Query query = entityManager.createNativeQuery("SELECT u.* FROM users as u WHERE u.enabled = 1", User.class);

    return query.getResultList();
  }

  @Override
  public List<User> getAllInActiveUsers() {
    Query query = entityManager.createNativeQuery("SELECT u.* FROM users as u WHERE u.enabled <> 1", User.class);

    return query.getResultList();
  }
}
