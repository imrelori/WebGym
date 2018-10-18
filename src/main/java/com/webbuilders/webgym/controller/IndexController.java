package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.domain.Category;
import com.webbuilders.webgym.repositories.CategoryRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;

    //constructor based DI
    public IndexController(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(){

        Optional<Category> categoryOptional = categoryRepository.findByDescription("Protein");

        System.out.println("The category is: " + categoryOptional.get().getId());

        return "index";
    }

}
