package com.drishti.accessmanagement.repository.dao.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.drishti.accessmanagement.repository.entity.user.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long>, UserRepositoryCustom {

  Optional<User> findByLoginId(String loginId);

  List<User> findByActiveTrue();

  List<User> findByActiveFalse();

//  List<User> findByNameStartingWith(String prefix);
//
//  List<User> findByNameEndingWith(String suffix);
//
//  List<User> findByNameContaining(String infix);
}
