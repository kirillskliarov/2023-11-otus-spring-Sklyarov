package ru.otus.hw.controllers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(HomeController.class)
public class HomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @Test
    @WithMockUser
    void shouldRedirect() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void shouldDenyRoot() throws Exception {
        mvc.perform(get("/"))
                .andExpect(status().is4xxClientError());
    }
}
