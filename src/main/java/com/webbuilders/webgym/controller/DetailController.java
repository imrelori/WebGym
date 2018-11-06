package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.services.DetailService;
import com.webbuilders.webgym.services.ProductService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DetailController {

    private final ProductService productService;
    private final DetailService detailService;

    public DetailController(ProductService productService, DetailService detailService) {
        this.productService = productService;
        this.detailService = detailService;
    }

    @GetMapping
    @RequestMapping("/product/{productId}/details")
    public String listDetails(@PathVariable String productId, Model model){

        // commandot használni a lazy load error elkerőlése végett Thymeleaf-ben.
        model.addAttribute("product", productService.findProductById(Long.valueOf(productId)));

        return "product/details/list"; //path a html-hez
    }

    @GetMapping
    @RequestMapping("/product/{productId}/details/{id}/show")
    public String showProductDetails(@PathVariable String productId,
                                       @PathVariable String id, Model model){
        model.addAttribute("details", detailService.findByProductIdAndDetailId(Long.valueOf(productId), Long.valueOf(id)));
        return "product/details/showDetail";
    }

}
