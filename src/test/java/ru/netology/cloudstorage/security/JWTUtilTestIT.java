package ru.netology.cloudstorage.security;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
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


    @Container
    static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>(DockerImageName.parse(
            "postgres:13.3"))
            .withExposedPorts(5432)
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");
//            .withInitScript("classpath:db.sql");


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
        generateToken = jwtUtil.generateToken(userName);
    }

    @Test
    void test() {
        assertTrue(container.isRunning());
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