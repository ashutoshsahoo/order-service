package com.ashu.order.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.order.auth.model.UserDao;

public interface UserRepository extends JpaRepository<UserDao, Long> {

	Optional<UserDao> findByUsername(String username);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

}
