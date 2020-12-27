package com.example.castservice.unit;

import com.example.castservice.model.Role;
import com.example.castservice.repository.RoleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class RoleControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RoleRepository roleRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenRole_whenGetRoleByName_thenReturnJsonRole() throws Exception {
        Role role1 = new Role("1", "Actor");

        given(roleRepository.findRoleByName("Actor")).willReturn(role1);

        mockMvc.perform(get("/role/{name}","Actor"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Actor")));
    }

    @Test
    public void whenPostRole_thenReturnJsonRole() throws Exception{
        Role role2 = new Role("2","Director");

        mockMvc.perform(post("/role")
                .content(mapper.writeValueAsString(role2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Director")));
    }

    @Test
    public void givenRole_whenPutRole_thenReturnJsonRole() throws Exception{
        Role role1 = new Role("1","Actor");

        given(roleRepository.findRoleById("1")).willReturn(role1);

        Role updatedRole = new Role("1","Actress");

        mockMvc.perform(put("/role")
                .content(mapper.writeValueAsString(updatedRole))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Actress")));
    }

    @Test
    public void givenRole_whenDeleteRole_thenStatusOk() throws Exception{
        Role roleToBeDeleted = new Role("999","Writer");

        given(roleRepository.findRoleById("999")).willReturn(roleToBeDeleted);

        mockMvc.perform(delete("/role/{id}","999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoRole_whenDeleteRole_thenStatusNotFound() throws Exception{
        given(roleRepository.findRoleById("888")).willReturn(null);

        mockMvc.perform(delete("/role/{id}","888")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}