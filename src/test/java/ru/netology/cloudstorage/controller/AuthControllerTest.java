package ru.netology.cloudstorage.controller;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.netology.cloudstorage.security.JWTFilter;
import ru.netology.cloudstorage.security.JWTUtil;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author VladSemikin
 */
@SpringBootTest(classes = {AuthController.class, JWTUtil.class})
@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JWTUtil jwtUtil;

    @MockBean
    private JWTFilter jwtFilter;

    @MockBean
    private AuthenticationManager authManager;

    @Test
    @WithMockUser(username = "user", password = "user")
    void loginHandler() throws Exception {

        String token = jwtUtil.generateToken("user");

        assertNotNull(token);
        mockMvc.perform(MockMvcRequestBuilders.get("/list")
                .header("auth-token", token)).andExpect(status().isOk());
    }
}