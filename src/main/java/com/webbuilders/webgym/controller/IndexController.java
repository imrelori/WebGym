package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
public class IndexController {

    private final ProductService productService;

    public IndexController(ProductService productService) {
        this.productService = productService;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model) {
    log.debug("Index page is up");

        model.addAttribute("products", productService.getProducts());

        return "index";
    }

}
