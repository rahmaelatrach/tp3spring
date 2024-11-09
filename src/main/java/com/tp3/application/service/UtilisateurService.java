package com.tp3.application.service;




import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tp3.application.modele.Role;
import com.tp3.application.modele.Utilisateur;
import com.tp3.application.modele.UtilisateurImage;
import com.tp3.application.repository.RoleRepository;
import com.tp3.application.repository.UtilisateurImageRepository;
import com.tp3.application.repository.UtilisateurRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UtilisateurService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UtilisateurImageRepository utilisateurImageRepository;

    // Récupération de tous les utilisateurs
    public List<Utilisateur> getAllUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    // Création d'un nouvel utilisateur avec un rôle associé
    public Utilisateur createUtilisateurWithRole(Utilisateur utilisateur, String roleName) {
        Optional<Role> role = roleRepository.findByNom(roleName);
        role.ifPresent(utilisateur::setRole);  // Associe le rôle si trouvé
        return utilisateurRepository.save(utilisateur);
    }

    // Récupération d'un utilisateur par ID
    public Optional<Utilisateur> getUtilisateurById(Long id) {
        return utilisateurRepository.findById(id);
    }

    // Association d'un rôle à un utilisateur
    public Optional<Utilisateur> assignRole(Long utilisateurId, Long roleId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(utilisateurId);
        Optional<Role> role = roleRepository.findById(roleId);

        if (utilisateur.isPresent() && role.isPresent()) {
            utilisateur.get().setRole(role.get());
            return Optional.of(utilisateurRepository.save(utilisateur.get()));
        }
        return Optional.empty();
    }

    // Ajout d'une image à un utilisateur
    public UtilisateurImage addImageToUtilisateur(Long utilisateurId, UtilisateurImage image) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(utilisateurId);
        if (utilisateur.isPresent()) {
            image.setUtilisateur(utilisateur.get());
            return utilisateurImageRepository.save(image);
        }
        throw new RuntimeException("Utilisateur non trouvé pour l'ID " + utilisateurId);
    }

    // Récupération des utilisateurs ayant un rôle spécifique
    public List<Utilisateur> getUtilisateursByRole(String roleName) {
        return utilisateurRepository.findByRoleNom(roleName);
    }

    // Suppression d'un utilisateur par ID
    public void deleteUtilisateurById(Long id) {
        utilisateurRepository.deleteById(id);
    }

    // Suppression d'un rôle par ID
    public void deleteRoleById(Long roleId) {
        roleRepository.deleteById(roleId);
    }

    // Suppression d'une image d'un utilisateur
    public void deleteImage(Long utilisateurId, Long imageId) {
        Optional<Utilisateur> utilisateur = utilisateurRepository.findById(utilisateurId);
        if (utilisateur.isPresent()) {
            Optional<UtilisateurImage> image = utilisateurImageRepository.findById(imageId);
            if (image.isPresent() && image.get().getUtilisateur().getId().equals(utilisateurId)) {
                utilisateurImageRepository.deleteById(imageId);
            } else {
                throw new RuntimeException("Image non trouvée pour l'utilisateur spécifié");
            }
        } else {
            throw new RuntimeException("Utilisateur non trouvé pour l'ID " + utilisateurId);
        }
    }
}

