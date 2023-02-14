package com.sportyshoe.admin.user.category;

import com.sportyshoe.admin.category.CategoryRepository;
import com.sportyshoe.admin.category.CategoryService;
import com.sportyshoe.common.entity.Category;
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
public class CategoryServiceTests {

    @MockBean
    private CategoryRepository repo;

    @InjectMocks
    private CategoryService service;

    @Test
    public void testCheckUniqueInNewModeReturnDuplicateName() {
        Integer id = null;
        String name = "Closed shoes, Boot";
        String alias = "Closed shoes, Boot";

        Category category = new Category(id, name, alias);

        Mockito.when(repo.findByName(name)).thenReturn(category);
        Mockito.when(repo.findByAlias(alias)).thenReturn(null);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void testCheckUniqueInNewModeReturnDuplicateAlias() {
        Integer id = null;
        String name = "Open shoes, Women";
        String alias = "Open shoes, Women";

        Category category = new Category(id, name, alias);

        Mockito.when(repo.findByName(name)).thenReturn(null);
        Mockito.when(repo.findByAlias(alias)).thenReturn(category);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateAlias");
    }


    @Test
    public void testCheckUniqueInNewModeReturnOK() {
        Integer id = null;
        String name = "Open shoes, Women";
        String alias = "Open shoes, Women";

        Mockito.when(repo.findByName(name)).thenReturn(null);
        Mockito.when(repo.findByAlias(alias)).thenReturn(null);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("OK");
    }

    @Test
    public void testCheckUniqueInEditModeReturnDuplicateName() {
        Integer id = 3;
        String name = "Open shoes, kids";
        String alias = "Open shoes, kids";

        Category category = new Category(2, name, alias);

        Mockito.when(repo.findByName(name)).thenReturn(category);
        Mockito.when(repo.findByAlias(alias)).thenReturn(null);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateName");
    }

    @Test
    public void testCheckUniqueInEditModeReturnDuplicateAlias() {
        Integer id = 3;
        String name = "Open shoes, kids";
        String alias = "Open shoes, kids";

        Category category = new Category(2, name, alias);

        Mockito.when(repo.findByName(name)).thenReturn(null);
        Mockito.when(repo.findByAlias(alias)).thenReturn(category);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("DuplicateAlias");
    }

    @Test
    public void testCheckUniqueInEditModeReturnOK() {
        Integer id = 3;
        String name = "Open shoes, kids";
        String alias = "Open shoes, kids";

        Category category = new Category(id, name, alias);

        Mockito.when(repo.findByName(name)).thenReturn(null);
        Mockito.when(repo.findByAlias(alias)).thenReturn(category);

        String result = service.checkUnique(id, name, alias);

        assertThat(result).isEqualTo("OK");
    }
}
