package com.example.castservice.unit;

import com.example.castservice.model.CastMember;
import com.example.castservice.repository.CastMemberRepository;
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
public class CastMemberControllerUnitTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CastMemberRepository castMemberRepository;

    private ObjectMapper mapper = new ObjectMapper();

    @Test
    public void givenCastMember_whenGetCastMemberByName_thenReturnJsonCastMember() throws Exception {
        CastMember castMember1 = new CastMember("1","Kopperman", ("11/11/2020"), "Belgisch", 1);

        given(castMemberRepository.findCastMemberByName("Kopperman")).willReturn(castMember1);

        mockMvc.perform(get("/castmember/name/{name}","Kopperman"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Kopperman")))
                .andExpect(jsonPath("$.birthDate",is(("11/11/2020"))))
                .andExpect(jsonPath("$.nationality",is("Belgisch")))
                .andExpect(jsonPath("$.roleId",is(1)));
    }

    @Test
    public void givenCastMember_whenGetCastMembersByRoleId_thenReturnJsonCastMembers() throws Exception {
        CastMember castMember1 = new CastMember("1","Kopperman", ("11/11/2020"), "Belgisch", 1);
        CastMember castMember2 = new CastMember("2","Jannes", ("11/01/2020"), "Nederlands", 1);

        List<CastMember> castMemberList = new ArrayList<>();
        castMemberList.add(castMember1);
        castMemberList.add(castMember2);

        given(castMemberRepository.findCastMemberByRoleId(1)).willReturn(castMemberList);

        mockMvc.perform(get("/castmember/role/{roleId}",1))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Kopperman")))
                .andExpect(jsonPath("$[0].birthDate",is(("11/11/2020"))))
                .andExpect(jsonPath("$[0].nationality",is("Belgisch")))
                .andExpect(jsonPath("$[1].name",is("Jannes")))
                .andExpect(jsonPath("$[1].birthDate",is(("11/01/2020"))))
                .andExpect(jsonPath("$[1].nationality",is("Nederlands")));
    }

    @Test
    public void givenCastMember_whenGetCastMembersByNationality_thenReturnJsonCastMembers() throws Exception {
        CastMember castMember1 = new CastMember("1","Kopperman", ("11/11/2020"), "Belgisch", 1);
        CastMember castMember2 = new CastMember("2","Joske", ("19/09/1998"), "Belgisch", 2);

        List<CastMember> castMemberList = new ArrayList<>();
        castMemberList.add(castMember1);
        castMemberList.add(castMember2);

        given(castMemberRepository.findCastMemberByNationality("Belgisch")).willReturn(castMemberList);

        mockMvc.perform(get("/castmember/nationality/{nationality}","Belgisch"))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name",is("Kopperman")))
                .andExpect(jsonPath("$[0].birthDate",is(("11/11/2020"))))
                .andExpect(jsonPath("$[0].roleId",is(1)))
                .andExpect(jsonPath("$[1].name",is("Joske")))
                .andExpect(jsonPath("$[1].birthDate",is(("19/09/1998"))))
                .andExpect(jsonPath("$[1].roleId",is(2)));
    }

    @Test
    public void whenPostCastMember_thenReturnJsonCastMember() throws Exception{
        CastMember castMember3 = new CastMember("3","Bertje", ("12/01/1990"), "Belgisch", 3);

        mockMvc.perform(post("/castmember")
                .content(mapper.writeValueAsString(castMember3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Bertje")))
                .andExpect(jsonPath("$.birthDate",is(("12/01/1990"))))
                .andExpect(jsonPath("$.nationality",is("Belgisch")))
                .andExpect(jsonPath("$.roleId",is(3)));
    }

    @Test
    public void givenCastMember_whenPutCastMember_thenReturnJsonCastMember() throws Exception{
        CastMember castMember1 = new CastMember("1","Kopperman", ("11/11/2020"), "Belgisch", 1);

        given(castMemberRepository.findCastMemberById("1")).willReturn(castMember1);

        CastMember updatedCastMember = new CastMember("1","Kopperman", ("11/11/2020"), "Nederlands", 2);

        mockMvc.perform(put("/castmember")
                .content(mapper.writeValueAsString(updatedCastMember))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name",is("Kopperman")))
                .andExpect(jsonPath("$.birthDate",is(("11/11/2020"))))
                .andExpect(jsonPath("$.nationality",is("Nederlands")))
                .andExpect(jsonPath("$.roleId",is(2)));
    }

    @Test
    public void givenCastMember_whenDeleteCastMember_thenStatusOk() throws Exception{
        CastMember castMemberToBeDeleted = new CastMember("999","Bob",("10/01/1991"), "Nederlands", 1);

        given(castMemberRepository.findCastMemberById("999")).willReturn(castMemberToBeDeleted);

        mockMvc.perform(delete("/castmember/{id}","999")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void givenNoCastMember_whenDeleteCastMember_thenStatusNotFound() throws Exception{
        given(castMemberRepository.findCastMemberById("888")).willReturn(null);

        mockMvc.perform(delete("/castMembers/{id}","888")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}