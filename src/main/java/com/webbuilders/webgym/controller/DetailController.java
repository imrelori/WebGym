package com.webbuilders.webgym.controller;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.services.DetailService;
import com.webbuilders.webgym.services.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
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

        model.addAttribute("product", productService.findProductById(Long.valueOf(productId)));

        return "product/details/list";
    }

    @GetMapping
    @RequestMapping("/product/{productId}/details/{id}/show")
    public String showProductDetails(@PathVariable String productId, @PathVariable String id, Model model){
        model.addAttribute("details", detailService.findByProductIdAndDetailId(Long.valueOf(productId), Long.valueOf(id)));
        return "product/details/showDetail";
    }

    @GetMapping
    @RequestMapping("product/{productId}/detail/new")
    public String newDetail(@PathVariable String productId, Model model){

        ProductCommand productCommand = productService.findCommandById(Long.valueOf(productId));

        DetailsCommand detailsCommand = new DetailsCommand();
        detailsCommand.setProductId(Long.valueOf(productId));

        if (detailsCommand != null) {
            model.addAttribute("details", detailsCommand);
        } else {
            model.addAttribute("details", new DetailsCommand());
        }

        return "product/details/detailform";
    }

    @PostMapping("product/{productId}/detail")
    public String saveOrUpdate(@ModelAttribute DetailsCommand command){
        DetailsCommand savedCommand = detailService.saveDetailCommand(command);

        log.debug("saved product id:" + savedCommand.getProductId());
        log.debug("saved detail id:" + savedCommand.getId());

        return "redirect:/product/" + savedCommand.getProductId() + "/detail/" + savedCommand.getId() + "/show";
    }
}
