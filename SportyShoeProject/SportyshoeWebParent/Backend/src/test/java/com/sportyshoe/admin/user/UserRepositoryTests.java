package com.sportyshoe.admin.user;

import com.sportyshoe.common.entity.Role;
import com.sportyshoe.common.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest(showSql = false)
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Rollback(false)
public class UserRepositoryTests {
    @Autowired
    private UserRepository repo;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void testCreateNewUserWithOneRole() {
        Role roleAdmin = entityManager.find(Role.class, 2);
        User userDuncanKaligs = new User("kaligs@yohunters.com", "kaligs2023", "Kaligs", "Duncan");
        userDuncanKaligs.addRole(roleAdmin);

        User savedUser = repo.save(userDuncanKaligs);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testCreateNewUserWithTwoRoles() {
        User userSheena = new User("sheena@gmail.com", "Shenna123", "A", "Sheena");
        Role roleEditor = new Role(4);
        Role roleAssistant = new Role(6);

        userSheena.addRole(roleEditor);
        userSheena.addRole(roleAssistant);

        User savedUser = repo.save(userSheena);

        assertThat(savedUser.getId()).isGreaterThan(0);
    }

    @Test
    public void testListAllUsers() {
        Iterable<User> listUsers = repo.findAll();
        listUsers.forEach(user -> System.out.println(user));
    }

    @Test
    public void testGetUserById() {
        User userDuncanKaligs = repo.findById(1).get();
        System.out.println(userDuncanKaligs);
        assertThat(userDuncanKaligs).isNotNull();
    }

    @Test
    public void testUpdateUserDetails() {
        User userDuncanKaligs = repo.findById(1).get();
        userDuncanKaligs.setEnabled(true);
        userDuncanKaligs.setEmail("dunkygeoffrey39@gmail.com");

        repo.save(userDuncanKaligs);
    }
    @Test
    public void testUpdateUserRoles() {
        User userSheena = repo.findById(2).get();
        Role roleEditor = new Role(4);
        Role roleSalesperson = new Role(3);

        userSheena.getRoles().remove(roleEditor);
        userSheena.addRole(roleSalesperson);

        repo.save(userSheena);
    }

    @Test
    public void testDeleteUser() {
        Integer userId = 2;
        repo.deleteById(userId);

    }

    @Test
    public void testGetUserByEmail() {
        String email = "dunkygeoffrey39@gmail.com";
        User user = repo.getUserByEmail(email);

        assertThat(user).isNotNull();
    }

    @Test
    public void testCountById() {
        Integer id = 1;
        Long countById = repo.countById(id);

        assertThat(countById).isNotNull().isGreaterThan(0);
    }

    @Test
    public void testDisableUser() {
        Integer id = 1;
        repo.updateEnabledStatus(id, false);

    }

    @Test
    public void testEnableUser() {
        Integer id = 3;
        repo.updateEnabledStatus(id, true);

    }

    @Test
    public void testListFirstPage() {
        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isEqualTo(pageSize);
    }

    @Test
    public void testSearchUsers() {
        String keyword = "Duncan";

        int pageNumber = 0;
        int pageSize = 4;

        Pageable pageable = PageRequest.of(pageNumber, pageSize);
        Page<User> page = repo.findAll(keyword, pageable);

        List<User> listUsers = page.getContent();

        listUsers.forEach(user -> System.out.println(user));

        assertThat(listUsers.size()).isGreaterThan(0);
    }
}
