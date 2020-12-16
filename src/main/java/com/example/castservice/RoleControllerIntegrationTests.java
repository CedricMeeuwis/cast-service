package com.example.castservice;

import com.example.castservice.model.Role;
import com.example.castservice.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoleRepository reviewRepository;

    private Role role1 = new Role("001", "Director");
    private Role role2 = new Role("002", "Camera man");
    private Role role3 = new Role("003", "Actor");
    private Role roleToDelete = new Role("004", "Stuntman");

    @BeforeEach
    public void beforeAllTests() {
        reviewRepository.deleteAll();
        reviewRepository.save(role1);
        reviewRepository.save(role2);
        reviewRepository.save(role3);
        reviewRepository.save(roleToDelete);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        reviewRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenRole_whenGetRoleByName_thenReturnJsonRole() throws Exception {
        mockMvc.perform(get("/role/{name}","Actor"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("003")))
                .andExpect(jsonPath("$.name", is("Actor")));
    }

    @Test
    public void whenPostRole_thenReturnJsonRole() throws Exception {
        Role role4 = new Role("005", "Writer");

        mockMvc.perform(post("/role")
                .content(mapper.writeValueAsString(role4))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("005")))
                .andExpect(jsonPath("$.name", is("Writer")));
    }

    @Test
    public void givenRole_whenPutRole_thenReturnJsonRole() throws Exception {

        Role updatedRole = new Role("001", "Regisseur");

        mockMvc.perform(put("/role")
                .content(mapper.writeValueAsString(updatedRole))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is("001")))
                .andExpect(jsonPath("$.name", is("Regisseur")));
    }

    @Test
    public void givenRole_whenDeleteRole_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/role/{id}", "004")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoRole_whenDeleteRole_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/rol/{id}", "999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}