package ru.netology.cloudstorage.security;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author VladSemikin
 */
@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
@Testcontainers
@Sql(value = {"/database-script/create-user-table-before.sql", "/database-script/create-user-before.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/database-script/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
@ContextConfiguration(initializers = {MyUserDetailsServiceTest.Initializer.class})
class MyUserDetailsServiceTest {

    @Container
    static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>
            (DockerImageName.parse("postgres:13.3"))
            .withExposedPorts(5432)
            .withDatabaseName("postgres")
            .withUsername("postgres")
            .withPassword("postgres");

    @Autowired
    private MyUserDetailsService userDetailsService;

    static class Initializer
            implements ApplicationContextInitializer<ConfigurableApplicationContext> {
        public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
            TestPropertyValues.of(
                    "spring.datasource.url=" + container.getJdbcUrl(),
                    "spring.datasource.username=" + container.getUsername(),
                    "spring.datasource.password=" + container.getPassword()
            ).applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    void loadUserByUsername() {
        String USERNAME = "user";
        assertEquals(USERNAME, userDetailsService.loadUserByUsername(USERNAME).getUsername());
    }
}