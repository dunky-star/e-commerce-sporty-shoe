package com.sportyshoe.admin.user.product;

import com.sportyshoe.admin.product.ProductRepository;
import com.sportyshoe.common.entity.Brand;
import com.sportyshoe.common.entity.Category;
import com.sportyshoe.common.entity.product.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;

import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class ProductRepositoryTests {

    @Autowired
    private ProductRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateProduct() {
        Brand brand = entityManager.find(Brand.class, 6);
        Category category = entityManager.find(Category.class, 9);

        Product product = new Product();
        product.setName("Jordan sneaker");
        product.setAlias("jordan_sneaker_nba");
        product.setShortDescription("Jordan sneaker sport wear for NBA players.");
        product.setFullDescription("Jordan sneaker sport wear for NBA players, the best shoe for basket ball game.");

        product.setBrand(brand);
        product.setCategory(category);

        product.setPrice(5000);
        product.setCost(4500);
        product.setEnabled(true);
        product.setInStock(true);

        product.setCreatedTime(new Date());
        product.setUpdatedTime(new Date());

        Product savedProduct = repo.save(product);

        assertThat(savedProduct).isNotNull();
        assertThat(savedProduct.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllProducts() {
        Iterable<Product> iterableProducts = repo.findAll();

        iterableProducts.forEach(System.out::println);
    }

    @Test
    public void testGetProduct() {
        Integer id = 1;
        Product product = repo.findById(id).get();
        System.out.println(product);


        assertThat(product).isNotNull();
    }

    @Test
    public void testUpdateProduct() {
        Integer id = 1;
        Product product = repo.findById(id).get();
        product.setPrice(499);

        repo.save(product);

        Product updatedProduct = entityManager.find(Product.class, id);

        assertThat(updatedProduct.getPrice()).isEqualTo(499);
    }

    @Test
    public void testDeleteProduct() {
        Integer id = 3;
        repo.deleteById(id);

        Optional<Product> result = repo.findById(id);

        assertThat(!result.isPresent());
    }

//    @Test
//    public void testSaveProductWithImages() {
//        Integer productId = 1;
//        Product product = repo.findById(productId).get();
//
//        product.setMainImage("main image.jpg");
//        product.addExtraImage("extra image 1.png");
//        product.addExtraImage("extra_image_2.png");
//        product.addExtraImage("extra-image3.png");
//
//        Product savedProduct = repo.save(product);
//
//        assertThat(savedProduct.getImages().size()).isEqualTo(3);
//    }

//    @Test
//    public void testSaveProductWithDetails() {
//        Integer productId = 1;
//        Product product = repo.findById(productId).get();
//
//        product.addDetail("Device Memory", "128 GB");
//        product.addDetail("CPU Model", "MediaTek");
//        product.addDetail("OS", "Android 10");
//
//        Product savedProduct = repo.save(product);
//        assertThat(savedProduct.getDetails()).isNotEmpty();
//    }

    @Test
    public void testUpdateReviewCountAndAverageRating() {
        Integer productId = 100;
        // repo.updateReviewCountAndAverageRating(productId);
    }
}
