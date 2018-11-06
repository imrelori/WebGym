package com.webbuilders.webgym.services;

import com.webbuilders.webgym.commands.ProductCommand;
import com.webbuilders.webgym.converters.ProductCommandToProduct;
import com.webbuilders.webgym.converters.ProductToProductCommand;
import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ProductCommandToProduct productCommandToProduct;
    private final ProductToProductCommand productToProductCommand;

    public ProductServiceImpl(ProductRepository productRepository, ProductCommandToProduct productCommandToProduct, ProductToProductCommand productToProductCommand) {
        this.productRepository = productRepository;
        this.productCommandToProduct = productCommandToProduct;
        this.productToProductCommand = productToProductCommand;
    }

    @Override
    public Set<Product> getProducts() {
        log.debug("The Service is getting the products");

        Set<Product> productSet = new HashSet<>();
        productRepository.findAll().iterator().forEachRemaining(productSet::add);
        return productSet;
    }

    @Override
    public Product findProductById(Long id) {

        Optional<Product> productOptional = productRepository.findById(id);

        if (!productOptional.isPresent()) {
            throw new RuntimeException("Product not found with id:" + id);
        }

        return productOptional.get();
    }

    @Override
    public ProductCommand findCommandById(Long l) {
        return productToProductCommand.convert(findProductById(l));
    }

    @Override
    public ProductCommand saveRecipeCommand(ProductCommand command) {
        Product detachedProduct = productCommandToProduct.convert(command);

        Product savedProduct = productRepository.save(detachedProduct);
        log.debug("Saved ProductId:" + savedProduct.getId());
        return productToProductCommand.convert(savedProduct);
    }

    @Override
    public void deleteById(Long idToDelete) {
        productRepository.deleteById(idToDelete);
    }
}
