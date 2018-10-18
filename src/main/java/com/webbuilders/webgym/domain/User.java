package com.webbuilders.webgym.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@EqualsAndHashCode(exclude = {"cart"})
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Cart cart;

    private String firstName;

    private String lastName;

    private String email;

    private String contactNumber;

    private String password;

    private boolean enabled;

}
