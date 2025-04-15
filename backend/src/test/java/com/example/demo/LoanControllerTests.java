package com.example.demo;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

import com.example.demo.db.DataEntity;
import com.example.demo.db.DataRepository;

@WebMvcTest
class LoanControllerTests {
    
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private DataRepository dataRepository;

    private static final ArrayList<DataEntity> loans = new ArrayList<>();

    @BeforeAll
    static void setup(){
        loans.add(new DataEntity(1L, "loan 1"));
        loans.add(new DataEntity(2L, "loan 2"));
        loans.add(new DataEntity(3L, "the best loan"));
    }

    @Test
    @WithMockUser
    void testLoans() throws Exception{

        when(dataRepository.findAll()).thenReturn(loans);

        mockMvc.perform(get("/loans"))
                .andExpect(status().isOk());

    }

    @WithMockUser
    @ParameterizedTest
    @ValueSource(longs = {1L, 2L, 3L})
    void testLoan(long id) throws Exception{

        when(dataRepository.findById(id)).thenReturn(Optional.of(loans.get((int)id-1)));

        mockMvc.perform(get(String.format("/loan/%d", id)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id));

    }

    @WithMockUser
    @Test
    void testLoan() throws Exception{

        when(dataRepository.findById(12L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/loan/12"))
                .andExpect(status().isNotFound());

    }

    @Test
    @WithMockUser
    void testPostLoan() throws Exception{

        DataEntity data = new DataEntity("loan 1");

        when(dataRepository.save(any(DataEntity.class))).thenReturn(data);

        mockMvc.perform(
            post("/loan")
                    .content("hello")
                    .contentType(MediaType.TEXT_PLAIN)
                    .with(csrf())
        ).andExpect(status().isCreated());

    }

    @Test
    @WithMockUser
    void testPutLoan() throws Exception{

        DataEntity data = new DataEntity(3L, "the best loan ever");

        when(dataRepository.save(any(DataEntity.class))).thenReturn(data);

        mockMvc.perform(
            put("/loan/3")
                    .content("the best loan ever")
                    .contentType(MediaType.TEXT_PLAIN)
                    .with(csrf())
        ).andExpect(status().isAccepted());

    }

    @Test
    @WithMockUser
    void testDeleteLoan() throws Exception{

        mockMvc.perform(
            delete("/loan/2")
                    .with(csrf())
        ).andExpect(status().isOk());

    }

    @WithMockUser
    @Test
    void testCSRF() throws Exception{

        mockMvc.perform(get("/csrf"))
                .andExpect(status().isOk());

    }

}
