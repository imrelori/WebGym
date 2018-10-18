package com.webbuilders.webgym.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Details {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Product product;

    private String flavor_name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "details")
    public Set<Components> ingredients = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "details") // kell ez ide?
    public Set<Components> allergen_info = new HashSet<>();

    private String dosage;

    public Details() {
    }

    public Details(String flavor_name, Set<Components> ingredients, Set<Components> allergen_info, String dosage) {
        this.flavor_name = flavor_name;
        this.ingredients = ingredients;
        this.allergen_info = allergen_info;
        this.dosage = dosage;
    }
}

