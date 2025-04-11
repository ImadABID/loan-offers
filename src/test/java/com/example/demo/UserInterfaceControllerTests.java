package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.demo.db.DataRepository;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class UserInterfaceControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockitoBean
    private DataRepository dataRepository;

    @Test
    @WithMockUser
    void testHome() throws Exception {

        mockMvc.perform(get("/"))
                .andExpect(status().isOk());

    }

}
