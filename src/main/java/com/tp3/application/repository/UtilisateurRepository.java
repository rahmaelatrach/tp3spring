package com.tp3.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp3.application.modele.Role;
import com.tp3.application.modele.Utilisateur;

import java.util.List;
import java.util.Optional;

public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {

    Optional<Utilisateur> findByEmail(String email);

    List<Utilisateur> findByRole(Role role);

    List<Utilisateur> findByRoleNom(String roleName);
}
