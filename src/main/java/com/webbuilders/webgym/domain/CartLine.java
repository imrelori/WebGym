package com.webbuilders.webgym.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"product", "cart"})
@Entity
public class CartLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //private Long cart_id;

    private Integer numberOfProduct;

    private Double price;

    private boolean available;

    @OneToOne
    private Product product;

    @ManyToOne
    private Cart cart;

}
