package com.webbuilders.webgym.commands;

import com.webbuilders.webgym.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class DetailsCommand {

        private Long id;
        private Product product;
        private String flavor_name;
        private String dosage;
        private String ingredient;
        private String allergen_info;
        private Long productId;
}
