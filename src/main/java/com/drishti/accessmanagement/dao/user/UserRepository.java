package com.drishti.accessmanagement.dao.user;

import com.drishti.accessmanagement.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

  User findByLoginId(String loginId);

}
