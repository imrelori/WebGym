package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

        log.debug("Products are showed up");

        return "product/show";
    }

    @GetMapping
    @RequestMapping("product/new")
    public String newProduct(Model model){
        model.addAttribute("product", new ProductCommand());

        log.debug("New product is created");

        return "product/productform";
    }

    @GetMapping
    @RequestMapping("product/{id}/update")
    public String updateProduct(@PathVariable String id, Model model){
        model.addAttribute("product", productService.findCommandById(Long.valueOf(id)));

        log.debug("Update the product");

        return "product/productform";
    }

    @PostMapping("product")
    public String saveOrUpdate(@ModelAttribute ProductCommand command){
        ProductCommand savedCommand = productService.saveRecipeCommand(command);

        log.debug("Save the product");

        return "redirect:/product/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("product/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Delete the product with id: " + id);

        productService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
