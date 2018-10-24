package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @RequestMapping("/product/{id}/show")
    public String showById(@PathVariable String id, Model model){

        model.addAttribute("product", productService.findProductById(new Long(id)));

        return "product/show";
    }
}
