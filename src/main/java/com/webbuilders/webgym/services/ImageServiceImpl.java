package com.webbuilders.webgym.services;

import com.webbuilders.webgym.domain.Product;
import com.webbuilders.webgym.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final ProductRepository productRepository;

    public ImageServiceImpl( ProductRepository productRepository) {

        this.productRepository = productRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long productId, MultipartFile file) {

        try {
            Product product = productRepository.findById(productId).get();

            Byte[] byteObjects = new Byte[file.getBytes().length];

            int i = 0;

            for (byte b : file.getBytes()){
                byteObjects[i++] = b;
            }

            product.setImage(byteObjects);

            productRepository.save(product);
        } catch (IOException e) {
            log.error("Something went wrong with image upload", e);

            e.printStackTrace();
        }
    }
}

