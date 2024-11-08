package com.tp3.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.tp3.application.modele.Utilisateur;
import com.tp3.application.modele.UtilisateurImage;
import com.tp3.application.service.UtilisateurService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/utilisateurs")
public class UtilisateurController {

    @Autowired
    private UtilisateurService utilisateurService;

    // Récupération de tous les utilisateurs
    @GetMapping
    public ResponseEntity<List<Utilisateur>> getAllUtilisateurs() {
        List<Utilisateur> utilisateurs = utilisateurService.getAllUtilisateurs();
        return ResponseEntity.ok(utilisateurs);
    }

    // Création d'un nouvel utilisateur avec un rôle associé
    @PostMapping
    public ResponseEntity<Utilisateur> createUtilisateur(@RequestBody Utilisateur utilisateur,
            @RequestParam String roleName) {
        Utilisateur newUtilisateur = utilisateurService.createUtilisateurWithRole(utilisateur, roleName);
        return ResponseEntity.ok(newUtilisateur);
    }

    // Récupération d'un utilisateur par ID
    @GetMapping("/{id}")
    public ResponseEntity<Utilisateur> getUtilisateurById(@PathVariable Long id) {
        Optional<Utilisateur> utilisateur = utilisateurService.getUtilisateurById(id);
        return utilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Association d'un rôle à un utilisateur
    @PutMapping("/{utilisateurId}/role/{roleId}")
    public ResponseEntity<Utilisateur> assignRoleToUtilisateur(
            @PathVariable Long utilisateurId, @PathVariable Long roleId) {
        Optional<Utilisateur> updatedUtilisateur = utilisateurService.assignRole(utilisateurId, roleId);
        return updatedUtilisateur.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Ajout d'une image à un utilisateur
    @PostMapping("/{utilisateurId}/image")
    public ResponseEntity<UtilisateurImage> addImageToUtilisateur(
            @PathVariable Long utilisateurId, @RequestBody UtilisateurImage utilisateurImage) {
        try {
            UtilisateurImage newImage = utilisateurService.addImageToUtilisateur(utilisateurId, utilisateurImage);
            return ResponseEntity.ok(newImage);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Récupération des utilisateurs ayant un rôle spécifique
    @GetMapping("/role/{roleName}")
    public ResponseEntity<List<Utilisateur>> getUtilisateursByRole(@PathVariable String roleName) {
        List<Utilisateur> utilisateurs = utilisateurService.getUtilisateursByRole(roleName);
        return ResponseEntity.ok(utilisateurs);
    }

    // Suppression d'un utilisateur par ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUtilisateur(@PathVariable Long id) {
        utilisateurService.deleteUtilisateurById(id);
        return ResponseEntity.noContent().build();
    }

    // Suppression d'un rôle par ID
    @DeleteMapping("/role/{roleId}")
    public ResponseEntity<Void> deleteRole(@PathVariable Long roleId) {
        utilisateurService.deleteRoleById(roleId);
        return ResponseEntity.noContent().build();
    }

    // Suppression d'une image d'un utilisateur
    @DeleteMapping("/{utilisateurId}/image/{imageId}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long utilisateurId, @PathVariable Long imageId) {
        utilisateurService.deleteImage(utilisateurId, imageId);
        return ResponseEntity.noContent().build();
    }
}
