package org.novi.languagelearner.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.novi.languagelearner.mappers.RoleMapper;
import org.novi.languagelearner.mappers.UserMapper;
import org.novi.languagelearner.repositories.RoleRepository;
import org.novi.languagelearner.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private UserMapper userMapper;

    @Mock
    private RoleMapper roleMapper;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository, roleRepository, userMapper, roleMapper, bCryptPasswordEncoder);
    }

    @Test
    public void testStreamExample() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        // call method
        userService.streamExample();

        // reset sysout to original
        System.setOut(originalOut);

        // convert output to string trimmed
        String actualOutput = outputStream.toString().trim();

        String expectedOutput = "Alicee\nBob\nCharlie";

        assertEquals(expectedOutput, actualOutput);
    }
}