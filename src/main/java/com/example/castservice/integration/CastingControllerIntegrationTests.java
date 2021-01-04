package com.example.castservice.integration;

import com.example.castservice.model.Casting;
import com.example.castservice.repository.CastingRepository;
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
public class CastingControllerIntegrationTests {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CastingRepository reviewRepository;

    private Casting casting1 = new Casting("001", 1, 1, ("31/12/1998"), ("28/01/1999"));
    private Casting casting2 = new Casting("002", 2, 1, ("28/12/1998"), ("26/01/1999"));
    private Casting casting3 = new Casting("003", 2, 2, ("05/06/2005"), ("30/08/2005"));
    private Casting castingToDelete = new Casting("999", 3, 3, ("01/01/2001"), ("02/01/2001"));

    @BeforeEach
    public void beforeAllTests() {
        reviewRepository.deleteAll();
        reviewRepository.save(casting1);
        reviewRepository.save(casting2);
        reviewRepository.save(casting3);
        reviewRepository.save(castingToDelete);
    }

    @AfterEach
    public void afterAllTests() {
        //Watch out with deleteAll() methods when you have other data in the test database!
        reviewRepository.deleteAll();
    }

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenCasting_whenGetCastingByMovie_thenReturnJsonCastings() throws Exception {
        List<Casting> castingList = new ArrayList<>();
        castingList.add(casting1);
        castingList.add(casting2);

        mockMvc.perform(get("/casting/movie/{movieId}", 1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].castMemberId", is(1)))
                .andExpect(jsonPath("$[0].startDate", is(("31/12/1998").toString())))
                .andExpect(jsonPath("$[0].endDate", is(("28/01/1999").toString())))
                .andExpect(jsonPath("$[1].castMemberId", is(2)))
                .andExpect(jsonPath("$[1].startDate", is(("28/12/1998").toString())))
                .andExpect(jsonPath("$[1].endDate", is(("26/01/1999").toString())));
    }

    @Test
    public void givenCasting_whenGetCastingsByCastMember_thenReturnJsonCastings() throws Exception {

        List<Casting> castingList = new ArrayList<>();
        castingList.add(casting2);
        castingList.add(casting3);

        mockMvc.perform(get("/casting/castmember/{casMemberId}", 2))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId", is(1)))
                .andExpect(jsonPath("$[0].startDate", is(("28/12/1998").toString())))
                .andExpect(jsonPath("$[0].endDate", is(("26/01/1999").toString())))
                .andExpect(jsonPath("$[1].movieId", is(2)))
                .andExpect(jsonPath("$[1].startDate", is(("05/06/2005").toString())))
                .andExpect(jsonPath("$[1].endDate", is(("30/08/2005").toString())));
    }

    @Test
    public void whenPostCasting_thenReturnJsonCasting() throws Exception {
        Casting casting4 = new Casting("004", 3, 4, ("20/09/2019"), ("30/09/2019"));

        mockMvc.perform(post("/casting")
                .content(mapper.writeValueAsString(casting4))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.castMemberId", is(3)))
                .andExpect(jsonPath("$.movieId", is(4)))
                .andExpect(jsonPath("$.startDate", is(("20/09/2019").toString())))
                .andExpect(jsonPath("$.endDate", is(("30/09/2019").toString())));
    }

    @Test
    public void givenCasting_whenPutCasting_thenReturnJsonCasting() throws Exception {

        Casting updatedCasting = new Casting("001", 1, 2, ("02/01/1999"), ("29/01/1999"));

        mockMvc.perform(put("/casting")
                .content(mapper.writeValueAsString(updatedCasting))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.castMemberId", is(1)))
                .andExpect(jsonPath("$.movieId", is(2)))
                .andExpect(jsonPath("$.startDate", is(("02/01/1999").toString())))
                .andExpect(jsonPath("$.endDate", is(("29/01/1999").toString())));
    }

    @Test
    public void givenCasting_whenDeleteCasting_thenStatusOk() throws Exception {
        mockMvc.perform(delete("/casting/{id}", "999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoCasting_whenDeleteCasting_thenStatusNotFound() throws Exception {
        mockMvc.perform(delete("/casting/{id}", "888")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

}