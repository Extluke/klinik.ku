package com.telemedclinic.user.repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.telemedclinic.user.entity.Role;
import com.telemedclinic.user.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    long countByRole(Role role);

    long countByActiveFalse();

    List<User> findTop5ByRoleInOrderByCreatedAtDesc(Collection<Role> roles);
}
