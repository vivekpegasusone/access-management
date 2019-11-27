package com.drishti.accessmanagement.dao.role;

import com.drishti.accessmanagement.entity.role.Role;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional(readOnly = true)
class RoleRepositoryImpl implements RoleRepositoryCustom {

  @PersistenceContext
  private EntityManager entityManager;


  @Override
  public List<Role> getAllActiveRoles() {
    Query query = entityManager.createNativeQuery("SELECT em.* FROM roles as em WHERE em.enabled = 1", Role.class);

    return query.getResultList();
  }

  @Override
  public List<Role> getAllInActiveRoles() {
    Query query = entityManager.createNativeQuery("SELECT em.* FROM roles as em WHERE em.enabled <> 1", Role.class);

    return query.getResultList();
  }

}
