package org.novi.languagelearner.repositories;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.novi.languagelearner.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void findByLastNameContainingIgnoreCaseAndRoles_RoleName() {

        // act
        List<User> users = userRepository.findByLastNameContainingIgnoreCaseAndRoles_RoleName("de Gro", "ROLE_ADMIN");

        assertEquals(1, users.size(), "Expected 1 user with lastName 'de Vries' and role 'ROLE_ADMIN'");
        assertTrue(users.stream().allMatch(user -> user.getUserName().equals("Kim")));
        assertTrue(users.getFirst().getRoles().stream().anyMatch(role -> role.getRoleName().equals("ROLE_ADMIN")));
    }


}