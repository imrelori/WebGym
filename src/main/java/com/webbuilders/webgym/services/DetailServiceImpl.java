package com.webbuilders.webgym.services;

import com.webbuilders.webgym.commands.DetailsCommand;
import com.webbuilders.webgym.converters.DetailToDetailCommand;
import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class DetailServiceImpl implements DetailService {

    private final ProductRepository productRepository;
    private final DetailToDetailCommand detailToDetailCommand;

    public DetailServiceImpl(ProductRepository productRepository, DetailToDetailCommand detailToDetailCommand) {
        this.productRepository = productRepository;
        this.detailToDetailCommand = detailToDetailCommand;
    }

    @Override
    public DetailsCommand findByProductIdAndDetailId(Long productId, Long detailId) {

        Optional<Product> productOptional = productRepository.findById(productId);

        if (!productOptional.isPresent()){
            //need some error handling by Ati
            log.error("product id not found. Id: " + productId);
            throw new RuntimeException("Product not found with id:" + productId);
        }

        Product product = productOptional.get();

        Optional<DetailsCommand> detailCommandOptional = product.getDetails().stream()
                .filter(detail -> detail.getId().equals(detailId))
                .map(detailToDetailCommand::convert).findFirst();

        if(!detailCommandOptional.isPresent()){
            //need some error handling by Ati
            log.error("Detail id not found: " + detailId);
            throw new RuntimeException("Detail not found with id:" + detailId);
        }

        return detailCommandOptional.get();
    }

}
