package ru.netology.cloudstorage.security;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.netology.cloudstorage.CloudStorageApplication;
import ru.netology.cloudstorage.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = CloudStorageApplication.class)
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Testcontainers
class JWTUtilTestIT {


    //    private final PostgreSQLContainer<?> POSTGRESQL_CONTAINER =
//            new PostgreSQLContainer<>("postgres:13.3")
//                    .withDatabaseName("postgres")
//                    .withPassword("postgres")
//                    .withUsername("postgres")
//                    .withExposedPorts(5432)
//                    .withInitScript("db.sql");
    @Container
    public static GenericContainer<?> restApp = new GenericContainer<>("postgres:13.3")
            .withExposedPorts(5432);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MockMvc mvc;

    private String generateToken;

    private final String userName = "user";


    @BeforeEach
    void setRestApp() {
        restApp.start();
        generateToken = jwtUtil.generateToken(userName);
    }

    @Test
    void generateToken() {
        assertNotNull(generateToken);
    }

    @Test
    void validateToken() {
        assertTrue(jwtUtil.validateToken(generateToken));
    }

    @Test
    void getUsername() {
        assertEquals(userName, jwtUtil.getUsername(generateToken));
    }

    @Test
    void getAuthentication() {
        Authentication authentication = jwtUtil.getAuthentication(generateToken);
        User user = (User) authentication.getPrincipal();
        assertEquals(userName, user.getUsername());
    }

    @Test
    void resolveToken() throws Exception {
        String resolveToken = jwtUtil.resolveToken(mvc.perform(get("/login")
                .header("auth-token", "Bearer " + generateToken)).andReturn().getRequest());
        assertEquals(resolveToken, generateToken);
    }
}