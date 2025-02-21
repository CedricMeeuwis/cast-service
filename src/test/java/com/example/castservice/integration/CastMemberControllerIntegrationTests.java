package com.example.castservice.integration;

import com.example.castservice.model.CastMember;
import com.example.castservice.repository.CastMemberRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CastMemberControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CastMemberRepository reviewRepository;

    private CastMember castMember1 = new CastMember("001", "Leonarda Dicaprio", ("11/11/1974"), "American", "Actor");
    private CastMember castMember2 = new CastMember("002", "Ryan Gosling", ("12/11/1980"), "Canadian", "Actor");
    private CastMember castMember3 = new CastMember("003", "Denis Villeneuve", ("03/10/1967"), "Canadian", "Director");
    private CastMember castMemberToDelete = new CastMember("004", "Johny Johnson", ("01/01/1968"), "Alien", "Soundman");

    @BeforeEach
    public void beforeAllTests() {
        reviewRepository.deleteAll();
        reviewRepository.save(castMember1);
        reviewRepository.save(castMember2);
        reviewRepository.save(castMember3);
        reviewRepository.save(castMemberToDelete);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        reviewRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenCastMember_whenGetCastMembersByRole_thenReturnJsonCastMembers() throws Exception {

        List<CastMember> castMemberList = new ArrayList<>();
        castMemberList.add(castMember1);
        castMemberList.add(castMember2);

        mockMvc.perform(get("/castmember/role/{role}", "Actor"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Leonarda Dicaprio")))
                .andExpect(jsonPath("$[0].birthDate", is(("11/11/1974"))))
                .andExpect(jsonPath("$[0].nationality", is("American")))
                .andExpect(jsonPath("$[1].name", is("Ryan Gosling")))
                .andExpect(jsonPath("$[1].birthDate", is(("12/11/1980"))))
                .andExpect(jsonPath("$[1].nationality", is("Canadian")));
    }

    @Test
    public void givenCastMember_whenGetCastMembersByNationality_thenReturnJsonCastMembers() throws Exception {

        List<CastMember> castMemberList = new ArrayList<>();
        castMemberList.add(castMember2);
        castMemberList.add(castMember3);

        mockMvc.perform(get("/castmember/nationality/{nationality}", "Canadian"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Ryan Gosling")))
                .andExpect(jsonPath("$[0].birthDate", is(("12/11/1980"))))
                .andExpect(jsonPath("$[0].role", is("Actor")))
                .andExpect(jsonPath("$[1].name", is("Denis Villeneuve")))
                .andExpect(jsonPath("$[1].birthDate", is(("03/10/1967"))))
                .andExpect(jsonPath("$[1].role", is("Director")));
    }

    @Test
    public void givenCastMember_whenGetCastMemberByName_thenReturnJsonCastMember() throws Exception {

        mockMvc.perform(get("/castmember/name/{name}", "Leonarda Dicaprio"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.birthDate", is(("11/11/1974"))))
                .andExpect(jsonPath("$.nationality", is("American")))
                .andExpect(jsonPath("$.role", is("Actor")));
    }

    @Test
    public void whenPostCastMember_thenReturnJsonCastMember() throws Exception {
        CastMember reviewUser3Book1 = new CastMember("005", "Amy Adams", ("20/08/1974"),"American", "Actor");

        mockMvc.perform(post("/castmember")
                .content(mapper.writeValueAsString(reviewUser3Book1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Amy Adams")))
                .andExpect(jsonPath("$.birthDate", is(("20/08/1974"))))
                .andExpect(jsonPath("$.nationality", is("American")))
                .andExpect(jsonPath("$.role", is("Actor")));
    }

    @Test
    public void givenCastMember_whenPutCastMember_thenReturnJsonCastMember() throws Exception {

        CastMember updatedCastMember = new CastMember("001", "Leonardo Dicaprio", ("11/11/1975"), "American!", "Actor");

        mockMvc.perform(put("/castmember")
                .content(mapper.writeValueAsString(updatedCastMember))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Leonardo Dicaprio")))
                .andExpect(jsonPath("$.birthDate", is(("11/11/1975"))))
                .andExpect(jsonPath("$.nationality", is("American!")))
                .andExpect(jsonPath("$.role", is("Actor")));
    }

    @Test
    public void givenCastMember_whenDeleteCastMember_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/castmember/{id}", "004")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoCastMember_whenDeleteCastMember_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/castmember/{id}", "999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}