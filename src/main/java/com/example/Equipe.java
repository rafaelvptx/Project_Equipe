package com.example;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Equipe {

    @OneToMany(mappedBy = "equipeHome")
    public Set<Match> matchs = new HashSet<>();

    @Id
    @GeneratedValue
    private Long id;

    public Set<Match> getBookmarks() {
        return matchs;
    }

    public Long getId() {
        return id;
    }

    public String getNomEquipe() {
        return nom;
    }

    @JsonIgnore
    public String nom;

    public Equipe(String name) {
        this.nom = name;
    }

    Equipe() { // jpa only
    }
}