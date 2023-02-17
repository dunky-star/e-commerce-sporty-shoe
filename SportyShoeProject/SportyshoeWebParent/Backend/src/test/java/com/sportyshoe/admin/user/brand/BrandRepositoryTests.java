package com.sportyshoe.admin.user.brand;

import com.sportyshoe.admin.brand.BrandRepository;
import com.sportyshoe.common.entity.Brand;
import com.sportyshoe.common.entity.Category;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class BrandRepositoryTests {

    @Autowired
    private BrandRepository repo;

    @Test
    public void testCreateBrand1() {
        Category Boots = new Category(7);
        Brand bata = new Brand("Bata");
        bata.getCategories().add(Boots);

        Brand savedBrand = repo.save(bata);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand2() {
        Category closedShoes = new Category(1);
        Category safariJungle = new Category(7);

        Brand uniliver = new Brand("Uniliver");
        uniliver.getCategories().add(closedShoes);
        uniliver.getCategories().add(safariJungle);

        Brand savedBrand = repo.save(uniliver);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateBrand3() {
        Brand roseFoam = new Brand("Rosefoam");

        roseFoam.getCategories().add(new Category(2));	// category memory
        roseFoam.getCategories().add(new Category(3));	// category internal hard drive

        Brand savedBrand = repo.save(roseFoam);

        assertThat(savedBrand).isNotNull();
        assertThat(savedBrand.getId()).isGreaterThan(0);
    }

    @Test
    public void testFindAll() {
        Iterable<Brand> brands = repo.findAll();
        brands.forEach(System.out::println);

        assertThat(brands).isNotEmpty();
    }

    @Test
    public void testGetById() {
        Brand brand = repo.findById(3).get();

        assertThat(brand.getName()).isEqualTo("Bata");
    }

    @Test
    public void testUpdateName() {
        String newName = "Adidas";
        Brand adidas = repo.findById(3).get();
        adidas.setName(newName);

        Brand savedBrand = repo.save(adidas);
        assertThat(savedBrand.getName()).isEqualTo(newName);
    }

    @Test
    public void testDelete() {
        Integer id = 2;
        repo.deleteById(id);

        Optional<Brand> result = repo.findById(id);

        assertThat(result.isEmpty());
    }
}

