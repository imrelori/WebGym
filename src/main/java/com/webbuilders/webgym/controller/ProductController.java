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
    @RequestMapping("recipe/new")
    public String newProduct(Model model){
        model.addAttribute("recipe", new ProductCommand());

        log.debug("New product is created");

        return "product/formofnewproduct";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateProduct(@PathVariable String id, Model model){
        model.addAttribute("recipe", productService.findCommandById(Long.valueOf(id)));

        log.debug("Update the product");

        return  "product/recipeform";
    }

    @PostMapping("recipe")
    public String saveOrUpdate(@ModelAttribute ProductCommand command){
        ProductCommand savedCommand = productService.saveRecipeCommand(command);

        log.debug("Save the product");

        return "redirect:/recipe/" + savedCommand.getId() + "/show";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id){

        log.debug("Delete the product with id: " + id);

        productService.deleteById(Long.valueOf(id));
        return "redirect:/";
    }
}
