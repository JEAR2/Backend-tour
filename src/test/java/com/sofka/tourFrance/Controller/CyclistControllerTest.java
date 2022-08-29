package com.sofka.tourFrance.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.tourFrance.Domain.Country;
import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Service.CyclistService;
import com.sofka.tourFrance.Service.TeamService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.*;

import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CyclistController.class)
class CyclistControllerTest {

     @Autowired
     private MockMvc mockMvc;

     @MockBean
     private CyclistService cyclistService;

     @MockBean
     private TeamService teamService;

     @Autowired
      ObjectMapper objectMapper;

     private Cyclist cyclist;


    @BeforeEach
    void setUp() {
        cyclist = new Cyclist();
        cyclist.setId(1L);
        cyclist.setName("John");
        cyclist.setCompetitorNumber("JO1");
        cyclist.setTeam(new Team());
        cyclist.setCountry(new Country());
    }

    @Test
    void getCyclist() throws Exception {
        Cyclist cyclist2 = new Cyclist(2L,"Edward","ED1",new Team(),new Country());
        List<Cyclist> cyclistSet = new ArrayList<>();
        cyclistSet.add(cyclist);
        cyclistSet.add(cyclist2);
        when(cyclistService.findAll()).thenReturn(cyclistSet);

        mockMvc.perform(get("/api/cyclist/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Edward"));

        verify(cyclistService,times(1)).findAll();
    }

    @Test
    void saveCyclist() throws Exception {

    }

    @Test
    void findCyclistById() throws Exception{
        when(cyclistService.findById(1L)).thenReturn(Optional.of(Optional.ofNullable(cyclist).orElseThrow()));
        mockMvc.perform(get("/api/cyclist/find/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.competitorNumber").value("JO1"));

        verify(cyclistService).findById(1L);
    }

    @Test
    void updateCyclist() throws Exception {
        Cyclist cyclist2 = new Cyclist(2L,"Edward","ED1",new Team(),new Country());
        when(cyclistService.update(any())).thenReturn(cyclist2);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/cyclist/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(cyclist2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(2)))
                .andExpect(jsonPath("$.name",is("Edward")))
                .andExpect( jsonPath("$.competitorNumber",is("ED1")));

        verify(cyclistService,times(1)).update(any());
    }

    @Test
    void deleteCyclist() throws Exception {
        cyclistService.delete(any());
        when(cyclistService.findById(cyclist.getId())).thenReturn(Optional.ofNullable(cyclist));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/cyclist/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(cyclistService,times(1)).delete(cyclist.getId());
    }
}