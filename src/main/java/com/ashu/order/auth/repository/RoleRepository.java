package com.ashu.order.auth.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ashu.order.auth.model.Role;
import com.ashu.order.auth.model.RoleType;

public interface RoleRepository extends JpaRepository<Role, Long> {

	Optional<Role> findByName(RoleType name);
}
