package ru.netology.cloudstorage.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Testcontainers
@Sql(value = {"/database-script/create-user-table-before.sql", "/database-script/create-user-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/database-script/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(initializers = {JWTUtilTest.Initializer.class})
class JWTUtilTest {

    @Autowired
    private JWTUtil jwtUtil;

    @Autowired
    private MockMvc mvc;

    private String generateToken;

    private final String USERNAME = "user";

    @Container
    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>
            (DockerImageName.parse("postgres:13.3"))
            .withExposedPorts(5432)
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @BeforeEach
    void setApp() {
        generateToken = jwtUtil.generateToken(USERNAME);
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
        assertEquals(USERNAME, jwtUtil.getUsername(generateToken));
    }

    @Test
    void getAuthentication() {
        Authentication authentication = jwtUtil.getAuthentication(generateToken);
        User user = (User) authentication.getPrincipal();
        assertEquals(USERNAME, user.getUsername());
    }

    @Test
    void resolveToken() throws Exception {
        String resolveToken = jwtUtil.resolveToken(mvc.perform(get("/login")
                .header("auth-token", "Bearer " + generateToken)).andReturn().getRequest());
        assertEquals(resolveToken, generateToken);
    }
}