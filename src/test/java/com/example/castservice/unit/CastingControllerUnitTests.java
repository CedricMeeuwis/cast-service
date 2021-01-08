package com.example.castservice.unit;

import com.example.castservice.model.Casting;
import com.example.castservice.repository.CastingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CastingControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CastingRepository castingRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenCasting_whenGetCastingsByMovieId_thenReturnJsonCastings() throws Exception {
        Casting casting1 = new Casting("1", "John", 1);
        Casting casting2 = new Casting("2", "Joe", 1);

        List<Casting> castingList = new ArrayList<>();
        castingList.add(casting1);
        castingList.add(casting2);

        given(castingRepository.findCastingByMovieId(1)).willReturn(castingList);

        mockMvc.perform(get("/casting/movie/{movieId}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].castMember",is("John")))
                .andExpect(jsonPath("$[1].castMember",is("Joe")));
    }

    @Test
    public void givenCasting_whenGetCastingsByCastMember_thenReturnJsonCastings() throws Exception {
        Casting casting1 = new Casting("1", "John", 1);
        Casting casting2 = new Casting("2", "John", 2);

        List<Casting> castingList = new ArrayList<>();
        castingList.add(casting1);
        castingList.add(casting2);

        given(castingRepository.findCastingByCastMember("John")).willReturn(castingList);

        mockMvc.perform(get("/casting/castmember/{castMember}","John"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].movieId",is(1)))
                .andExpect(jsonPath("$[1].movieId",is(2)));
    }

    @Test
    public void whenPostCasting_thenReturnJsonCasting() throws Exception{
        Casting casting3 = new Casting("3", "Joe", 3);

        mockMvc.perform(post("/casting")
                .content(mapper.writeValueAsString(casting3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.castMember",is("Joe")))
                .andExpect(jsonPath("$.movieId",is(3)));
    }

    @Test
    public void givenCasting_whenPutCasting_thenReturnJsonCasting() throws Exception{
        Casting casting1 = new Casting("1", "John", 1);

        given(castingRepository.findCastingById("1")).willReturn(casting1);

        Casting updatedCasting = new Casting("1", "John", 4);

        mockMvc.perform(put("/casting")
                .content(mapper.writeValueAsString(updatedCasting))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.castMember",is("John")))
                .andExpect(jsonPath("$.movieId",is(4)));
    }

    @Test
    public void givenCasting_whenDeleteCasting_thenStatusOk() throws Exception{
        Casting castingToBeDeleted = new Casting("999","Jo",9);

        given(castingRepository.findCastingById("999")).willReturn(castingToBeDeleted);

        mockMvc.perform(delete("/casting/{id}","999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoCasting_whenDeleteCasting_thenStatusNotFound() throws Exception{
        given(castingRepository.findCastingById("888")).willReturn(null);

        mockMvc.perform(delete("/casting/{id}","888")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}