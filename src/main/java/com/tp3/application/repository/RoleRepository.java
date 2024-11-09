package com.tp3.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp3.application.modele.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByNom(String nom);
}
