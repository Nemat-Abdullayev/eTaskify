package com.etaskify.repository;

import com.etaskify.enums.RoleName;
import com.etaskify.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByNameAndActiveTrue(RoleName name);
}
