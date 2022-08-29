package com.sofka.tourFrance.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sofka.tourFrance.Domain.Country;
import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
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
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TeamController.class)
class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TeamService teamService;

    @Autowired
    ObjectMapper objectMapper;

    private Team team;

    @BeforeEach
    void setUp() {
        team = new Team();
        team.setId(1L);
        team.setName("Equipo1");
        team.setCodeTeam("EQ1");
        team.setCountry(new Country());
        team.setCyclistsList(new HashSet<>());
    }

    @Test
    void findAll() throws Exception{
        List<Team> teamSet = new ArrayList<>();
        Team team2 = new Team();
        team2.setId(2L);
        team2.setName("Equipo2");
        team2.setCodeTeam("EQ2");
        team2.setCountry(new Country());
        team2.setCyclistsList(new HashSet<>());
        teamSet.add(team);
        teamSet.add(team2);
        when(teamService.findAll()).thenReturn(teamSet);

        mockMvc.perform(get("/api/team/all").contentType(MediaType.APPLICATION_JSON))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("Equipo1"))
                .andExpect(jsonPath("$[1].name").value("Equipo2"));

        verify(teamService,times(1)).findAll();
    }

    @Test
    void saveTeam() throws Exception {
        Team team2 = new Team();
        team2.setId(null);
        team2.setName("Equipo2");
        team2.setCodeTeam("EQ2");
        team2.setCountry(new Country());
        team2.setCyclistsList(new HashSet<>());
        when(teamService.save(any())).then(element->{
            Team t = element.getArgument(0);
            t.setId(2L);
            return t;
        }).thenReturn(team2);
        mockMvc.perform(post("/api/team/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(team2)))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith("application/json"))
                .andExpect(jsonPath("$.id",is(2)))
                .andExpect(jsonPath("$.name",is("Equipo2")))
                .andExpect( jsonPath("$.codeTeam",is("EQ2")));

        verify(teamService,times(1)).save(any());
    }

    @Test
    void findTeamById() throws Exception{
        when(teamService.findById(1L)).thenReturn(Optional.of(Optional.ofNullable(team).orElseThrow()));
        mockMvc.perform(get("/api/team/find/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.name").value("Equipo1"))
                .andExpect(jsonPath("$.codeTeam").value("EQ1"));

        verify(teamService).findById(1L);
    }

    @Test
    void updateTeam() throws Exception {
        Team team2 = new Team(2L,"Equipo2","EQ2",new Country(),new HashSet<>());
        when(teamService.update(any())).thenReturn(team2);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/team/update/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(this.objectMapper.writeValueAsString(team2)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id",is(2)))
                .andExpect(jsonPath("$.name",is("Equipo2")))
                .andExpect( jsonPath("$.codeTeam",is("EQ2")));

        verify(teamService,times(1)).update(any());
    }

    @Test
    void deleteTeam() throws Exception{
        teamService.delete(any());
        when(teamService.findById(team.getId())).thenReturn(Optional.ofNullable(team));
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/team/delete/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(teamService,times(1)).delete(team.getId());
    }

    @Test
    void findCyclist() throws Exception{
        Cyclist cyclist = new Cyclist(1L,"John","JO1",team,new Country());
        Cyclist cyclist2 = new Cyclist(2L,"Edward","ED1",team,new Country());
        Set<Cyclist> cyclistSet = new LinkedHashSet<>();
        cyclistSet.add(cyclist);
        cyclistSet.add(cyclist2);
        when(teamService.listCyclistCodeTeam(team.getCodeTeam())).thenReturn(cyclistSet);

        mockMvc.perform(get("/api/team/find/cyclists/EQ1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Edward"));

        verify(teamService).listCyclistCodeTeam("EQ1");
    }
}