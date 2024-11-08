package com.tp3.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tp3.application.modele.UtilisateurImage;

import java.util.Optional;

public interface UtilisateurImageRepository extends JpaRepository<UtilisateurImage,Long> {
    Optional<UtilisateurImage> findByUtilisateur(UtilisateurImage utilisateurImage);
}
