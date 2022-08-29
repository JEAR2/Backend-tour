package com.sofka.tourFrance.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.tourFrance.Domain.Country;
import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Service.CountryService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.*;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CountryController.class)
@Slf4j
class CountryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CountryService countryService;

    private Country country;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setup(){
        country = new Country();
        country.setId(1L);
        country.setName("Colombia");
        country.setCodeCountry("CO");
        country.setCyclistList(new HashSet<>());
        country.setTeamList(new HashSet<>());
    }

    @Test
    void getCountries() throws Exception {
        Country country2 = new Country();
        country2.setId(2L);
        country2.setName("Brasil");
        country2.setCodeCountry("BR");
        country2.setCyclistList(new HashSet<>());
        country2.setTeamList(new HashSet<>());

        List<Country> countryList = new ArrayList<>();
        countryList.add(country);
        countryList.add(country2);

        when(countryService.findAll()).thenReturn(countryList);

        mockMvc.perform(get("/api/country/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Colombia"))
                .andExpect(jsonPath("$[0].codeCountry").value("CO"))
                .andExpect(jsonPath("$[1].name").value("Brasil"))
                .andExpect(jsonPath("$[1].codeCountry").value("BR"));

        verify(countryService).findAll();

    }

    @Test
    void findCountryById() throws Exception {

        when(countryService.findById(1L)).thenReturn(Optional.of(Optional.ofNullable(country).orElseThrow()));
        mockMvc.perform(get("/api/country/find/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Colombia"))
                .andExpect(jsonPath("$.codeCountry").value("CO"));

        verify(countryService).findById(1L);
    }


    @Test
    void saveCountry() throws Exception {
        Country country2 = new Country(null,"Brasil","BR",new HashSet<>(),new HashSet<>());

        when(countryService.save(any())).then(element->{
            Country c = element.getArgument(0);
            c.setId(2L);
            return c;
        }).thenReturn(country2);
        mockMvc.perform(post("/api/country/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(country2)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id",is(2)))
                .andExpect(jsonPath("$.name",is("Brasil")))
                .andExpect( jsonPath("$.codeCountry",is("BR")));

        verify(countryService,times(1)).save(any());

    }


    @Test
    void deleteCountry() throws Exception {
        countryService.delete(any());
        when(countryService.findById(country.getId())).thenReturn(Optional.ofNullable(country));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/country/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(countryService,times(1)).delete(country.getId());
    }

    @Test
    void updateCountry() throws Exception {
        Country country2 = new Country(2L,"Brasil","BR",new HashSet<>(),new HashSet<>());

        when(countryService.update(any())).thenReturn(country2);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/country/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(country2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(2)))
                .andExpect(jsonPath("$.name",is("Brasil")))
                .andExpect( jsonPath("$.codeCountry",is("BR")));

        verify(countryService,times(1)).update(any());
    }

    @Test
    void getCyclistsByCountry() throws Exception {
        Cyclist cyclist = new Cyclist(1L,"John","JO1",new Team(),country);
        Cyclist cyclist2 = new Cyclist(2L,"Edward","ED1",new Team(),country);
        Set<Cyclist> cyclistSet = new LinkedHashSet<>();
        cyclistSet.add(cyclist);
        cyclistSet.add(cyclist2);
        when(countryService.getCyclistByCountry(1L)).thenReturn(cyclistSet);
        mockMvc.perform(get("/api/country/find/cyclist/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Edward"));

        verify(countryService).getCyclistByCountry(1L);
    }

    @Test
    void getTeamsByCountry() throws Exception {
        Team team = new Team(1L,"Equipo1","EQ1",country,new HashSet<>());
        Team team2 = new Team(2L,"Equipo2","EQ2",country,new HashSet<>());
        Set<Team> teamSet = new LinkedHashSet<>();
        teamSet.add(team);
        teamSet.add(team2);
        when(countryService.getTeamsByCountry(1L)).thenReturn(teamSet);
        mockMvc.perform(get("/api/country/find/teams/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Equipo1"))
                .andExpect(jsonPath("$[1].name").value("Equipo2"));
        verify(countryService).getTeamsByCountry(1L);
    }

    @Test
    void getTeamsByCountryCode() throws Exception{
        Team team = new Team(1L,"Equipo1","EQ1",country,new HashSet<>());
        Team team2 = new Team(2L,"Equipo2","EQ2",country,new HashSet<>());
        Set<Team> teamSet = new LinkedHashSet<>();
        teamSet.add(team);
        teamSet.add(team2);
        when(countryService.getTeamsByCountryCode(any())).thenReturn(teamSet);
        mockMvc.perform(get("/api/country/find/teams/code/CO").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Equipo1"))
                .andExpect(jsonPath("$[1].name").value("Equipo2"));
        verify(countryService).getTeamsByCountryCode(country.getCodeCountry());
    }
}