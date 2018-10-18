package com.webbuilders.webgym.domain;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String url;

    private Integer servings;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "product")
    private Set<Details> details = new HashSet<>();

    private String pack;

    @Lob
    private String description;

    private Integer price;

    @OneToOne(cascade = CascadeType.ALL)
    private CartLine cartLine;

    @Lob
    private Byte[] image;

    @Enumerated(value = EnumType.STRING)
    private Level level;

    @ManyToMany
    @JoinTable(name = "product_category",
            joinColumns = @JoinColumn(name = "product_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public Product addDetails(Details detail) {
        detail.setProduct(this);
        this.details.add(detail);
        return this;
    }
}
