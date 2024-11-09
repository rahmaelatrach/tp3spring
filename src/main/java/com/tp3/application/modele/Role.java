package com.tp3.application.modele;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nom;

    @OneToMany(mappedBy = "role", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<Utilisateur> utilisateurs;


    public Role() {
        super();
    }
}
