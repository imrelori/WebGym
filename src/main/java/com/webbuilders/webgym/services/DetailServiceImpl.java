package com.webbuilders.webgym.services;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.converters.DetailCommandToDetail;
import com.webbuilders.webgym.converters.DetailToDetailCommand;
import com.webbuilders.webgym.domain.Details;
import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class DetailServiceImpl implements DetailService {

    private final ProductRepository productRepository;
    private final DetailToDetailCommand detailToDetailCommand;
    private final DetailCommandToDetail detailCommandToDetail;

    public DetailServiceImpl(ProductRepository productRepository, DetailToDetailCommand detailToDetailCommand, DetailCommandToDetail detailCommandToDetail) {
        this.productRepository = productRepository;
        this.detailToDetailCommand = detailToDetailCommand;
        this.detailCommandToDetail = detailCommandToDetail;
    }

    @Override
    public DetailsCommand findByProductIdAndDetailId(Long productId, Long detailId) {

        Optional<Product> productOptional = productRepository.findById(productId);

        if (!productOptional.isPresent()){
            //need some error handling
            log.error("product id not found. Id: " + productId);
            throw new RuntimeException("Product not found with id:" + productId);
        }

        Product product = productOptional.get();

        Optional<DetailsCommand> detailCommandOptional = product.getDetails().stream()
                .filter(detail -> detail.getId().equals(detailId))
                .map(detailToDetailCommand::convert).findFirst();

        if(!detailCommandOptional.isPresent()){
            //need some error handling
            log.error("Detail id not found: " + detailId);
            throw new RuntimeException("Detail not found with id:" + detailId);
        }

        return detailCommandOptional.get();
    }

    @Override
    @Transactional
    public DetailsCommand saveDetailCommand(DetailsCommand command) {
        Optional<Product> productOptional = productRepository.findById(command.getProductId());

        if(!productOptional.isPresent()){

            log.error("Product not found for id: " + command.getProductId());
            return new DetailsCommand();
        } else {
            Product product= productOptional.get();

            Optional<Details> detailsOptional = product
                    .getDetails()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();

            if(detailsOptional.isPresent()){
                Details detailFound = detailsOptional.get();
                detailFound.setIngredients(command.getIngredient());
                detailFound.setDosage(command.getDosage());
                detailFound.setAllergen_info(command.getAllergen_info());
                detailFound.setFlavor_name(command.getFlavor_name());
            } else {
                //add new Detail
                Details details= detailCommandToDetail.convert(command);
                assert details != null;
                details.setProduct(product);
                product.addDetails(details);
            }

            Product savedProduct = productRepository.save(product);

            Optional<Details> savedDetailOptional = savedProduct.getDetails().stream()
                    .filter(productDetails -> productDetails.getId().equals(command.getId()))
                    .findFirst();

            if(!savedDetailOptional.isPresent()){
                savedDetailOptional = savedProduct.getDetails().stream()
                        .filter(productDetails -> productDetails.getIngredients().equals(command.getIngredient()))
                        .filter(productDetails -> productDetails.getAllergen_info().equals(command.getAllergen_info()))
                        .filter(productDetails -> productDetails.getFlavor_name().equals(command.getFlavor_name()))
                        .filter(productDetails -> productDetails.getDosage().equals(command.getDosage()))
                        .findFirst();
            }

            return detailToDetailCommand.convert(savedDetailOptional.get());
        }

    }

}

