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

    @Lob
    public String ingredients;

    public String allergen_info;

    private String dosage;

    public Details() {
    }

    public Details(String flavor_name, String ingredients, String allergen_info, String dosage) {
        this.flavor_name = flavor_name;
        this.ingredients = ingredients;
        this.allergen_info = allergen_info;
        this.dosage = dosage;
    }

    public Details(Product product, String flavor_name, String ingredients, String allergen_info, String dosage) {
        this.product = product;
        this.flavor_name = flavor_name;
        this.ingredients = ingredients;
        this.allergen_info = allergen_info;
        this.dosage = dosage;
    }
}

