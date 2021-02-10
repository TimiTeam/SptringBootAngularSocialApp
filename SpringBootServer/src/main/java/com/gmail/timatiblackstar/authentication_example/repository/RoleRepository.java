package com.gmail.timatiblackstar.authentication_example.repository;

import com.gmail.timatiblackstar.authentication_example.models.ERole;
import com.gmail.timatiblackstar.authentication_example.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(ERole name);
}
