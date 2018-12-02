package com.webbuilders.webgym.bootstrap;

import com.webbuilders.webgym.domain.*;
import com.webbuilders.webgym.repositories.CategoryRepository;
import com.webbuilders.webgym.repositories.ProductRepository;
import com.webbuilders.webgym.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class ProductBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public ProductBootstrap(CategoryRepository categoryRepository, ProductRepository productRepository, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {
        productRepository.saveAll(getProducts());
        log.debug("Booting The Data");
    }

    private List<Product> getProducts() {

        List<Product> products = new ArrayList<>(2);

        Optional<Category> proteinCategoryOptional = categoryRepository.findByDescription("Protein");

        if (!proteinCategoryOptional.isPresent()){
            throw new RuntimeException("Expected Category Not Found");
        }

        Category proteinCategory = proteinCategoryOptional.get();

        /*Optional<User> adminUserOptional = userRepository.findByRole("admin");

        if (!adminUserOptional.isPresent()){
            throw new RuntimeException("Expected User Not Found");
        }

        User adminUser = adminUserOptional.get();*/

        Product beefProtein = new Product();
        beefProtein.setName("Jumbo Hardcore");
        beefProtein.setPrice(50);
        beefProtein.setServings(20);
        beefProtein.setPack("3060 g");
        beefProtein.setLevel(Level.ADVANCED);
        beefProtein.setUrl("https://uk.scitecnutrition.com/products/jumbo-hardcore");
        beefProtein.setDescription("Jumbo Hardcore is our most advanced Mass Gainer. With 46 active ingredients, this truly is the most hardcore of all gainers.\n" +
                "\n" +
                "Its unique blend of high-quality protein, carbohydrates and creatines make it the go-to gainer for those looking for serious mass. It's often difficult to add quality calories from just food alone. Adding Jumbo Hardcore to your routine makes meeting your intake goals that little bit easier. \n" +
                "\n" +
                "A complete product ideal for those who want everything they need in one convenient product. The ultimate mass gainer with high-quality whey protein, multi-source carbohydrates and 6 types of creatine. \n" +
                "Read more: https://uk.scitecnutrition.com/products/jumbo-hardcore");

        Details beefProteinDetails = new Details();
        Components beef = new Components();
        beef.setName("beef");
        //beefProteinDetails.ingredients.add(beef);
        //beefProteinDetails.allergen_info.add(beef);

        beefProtein.addDetails(new Details("Brownie Praline",
                "Whey Protein Concentrate (from Milk), Maltodextrin, Oat Flour (Gluten), Flavors (Brownie, Chocolate Praline), Fat-reduced Cocoa Powder (10-12%), Dextrose, Hydrolyzed Beef Protein, Creatine Anhydrous, MicronTec Micronized Creatine Monohydrate, Rice Protein, Palatinose™ Isomaltulose, Taurine, Waxy Maize Starch, L-Glutamine, Highly Branched Cyclic Dextrin, Micronized L-Arginine Base, Micronized L-Leucine, Micellar Casein Concentrate (from Milk), ModCarb™ Gluten Free Grain Blend (Oat, Amaranth, Buckwheat, Millet, Quinoa), Instantized Whey Protein Isolate (from Milk, Emulsier: Soy Lecithin), Micronized L-Isoleucine, Micronized L-Valine, Thickener (Xanthan Gum), Sweeteners (Sucralose, Acesulfame K), Beta-Alanine, L-Ascorbic Acid, Betaine HCl, CreaPep™ [Partially Hydrolyzed Whey Protein, Micellar Casein (from Milk, Emulsier: Soy Lecithin)], Creatine Monohydrate, Glycine, L-Alanine, L-Arginine Alpha-Ketoglutarate 2:1, L-Lysine HCl, L-Phenylalanine, NOP-47™ Milk Protein Hydrolyzate, Creatine Citrate, Creatine Pyruvate, Kre-Alkalyn® (buered Creatine Monohydrate), L-Carnitine L-Tartrate, L-Ornithine HCl, Alpha-Ketoglutarate Calcium, Alpha Lipoic Acid, Garcinia mangostana (Fruit Skin) 10:1 extract, Trifolium pratense (Leaf and Flower) 30:1 extract, Bioperine® (Piper fruit 50:1 extract), Cholecalciferol.",
                "Manufactured in a facility that processes milk, egg, gluten, soy, peanuts, nuts, celery, fish and crustacean ingredients.",
                "one scope after workout"));

        beefProtein.getCategories().add(proteinCategory);

        products.add(beefProtein);

        return products;
    }
}
