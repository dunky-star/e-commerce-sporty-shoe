package com.sportyshoe.admin.user.brand;

import com.sportyshoe.admin.brand.BrandRepository;
import com.sportyshoe.admin.brand.BrandService;
import com.sportyshoe.common.entity.Brand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
public class BrandServiceTests {

    @MockBean
    private BrandRepository repo;

    @InjectMocks
    private BrandService service;

    @Test
    public void testCheckUniqueInNewModeReturnDuplicate() {
        Integer id = null;
        String name = "Adidas";
        Brand brand = new Brand(name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(id, name);
        assertThat(result).isEqualTo("Duplicate");
    }

    @Test
    public void testCheckUniqueInNewModeReturnOK() {
        Integer id = null;
        String name = "Converse";

        Mockito.when(repo.findByName(name)).thenReturn(null);

        String result = service.checkUnique(id, name);
        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void testCheckUniqueInEditModeReturnDuplicate() {
        Integer id = 3;
        String name = "Adidas";
        Brand brand = new Brand(id, name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(3, "Adidas");
        assertThat(result).isEqualTo("Duplicate");
    }

    @Test
    public void testCheckUniqueInEditModeReturnOK() {
        Integer id = 3;
        String name = "Adidas";
        Brand brand = new Brand(id, name);

        Mockito.when(repo.findByName(name)).thenReturn(brand);

        String result = service.checkUnique(id, "Adidas");
        assertThat(result).isEqualTo("OK");
    }
}

