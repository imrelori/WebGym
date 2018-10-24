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
        beefProtein.setName("Beef Protein");
        beefProtein.setPrice(6990);
        beefProtein.setServings(33);
        beefProtein.setPack("1000 g");
        beefProtein.setLevel(Level.ADVANCED);
        beefProtein.setDescription("A 100% BEEF CONCENTRATE kiváló alternatívája a tejfehérjéknek, \n" +
                "és különösen az ellentmondásos szójafehérjéknek. \n" +
                "Számos embernek okoz komoly problémát a laktóz-érzékenység vagy a tejallergia, \n" +
                "míg mások a paleolit diéta bizonyos formáját követik, ahol a tejtermékeket kizárják és \n" +
                "a hús alapú fehérje forrásokat priorizálják. (A marhahús alapanyag nem tartalmaz laktózt,\n " +
                "de a terméket laktózt tartalmazó tejterméket feldolgozó üzemben állítják elő!) \n" +
                "A marhafehérjénkben nincs hozzáadott kreatin. Minek kötnéd a kreatin-használatot és \n" +
                "annak pontos (vagy éppen hogy nem pontos) adagolását fehérjetermékhez? \n" +
                "Mi nem látjuk ennek értelmét!\n" +
                "\n" +
                "Read more: https://www.scitecnutrition.com/hu/termekek/scitec_nutrition/feherjek/100_beef_concentrate");

        Details beefProteinDetails = new Details();
        Components beef = new Components();
        beef.setName("beef");
        beefProteinDetails.ingredients.add(beef);
        beefProteinDetails.allergen_info.add(beef);

        beefProtein.addDetails(new Details("Chocolate", beefProteinDetails.ingredients, beefProteinDetails.allergen_info, "one scope after workout"));

        beefProtein.getCategories().add(proteinCategory);

        products.add(beefProtein);

        return products;
    }
}
