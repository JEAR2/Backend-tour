package com.sofka.tourFrance.Service;

import com.sofka.tourFrance.Domain.Country;
import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Repository.ITeamRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.SecondaryTable;
import java.sql.Array;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TeamServiceTest {

    @Mock
    private ITeamRepository iTeamRepository;

    @InjectMocks
    private TeamService teamService;

    private Team team;


    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        team =  new Team();
        team.setId(1L);
        team.setName("team One");
        team.setCodeTeam("TO");
        team.setCountry(new Country());
        team.setCyclistsList(new HashSet<>());
    }

    @Test
    void findAll() {
        when(iTeamRepository.findAll()).thenReturn(Arrays.asList(team));
        assertNotNull(teamService.findAll());
    }

    @Test
    void findById() {
        when(iTeamRepository.findById(team.getId())).thenReturn(Optional.ofNullable(team));
        assertNotNull(teamService.findById(team.getId()));
    }

    @Test
    void save() {
        when(iTeamRepository.save(team)).thenReturn(team);
        assertNotNull(teamService.save(team));
        verify(iTeamRepository,times(1)).save(team);
    }

    @Test
    void update() {
        when(iTeamRepository.save(team)).thenReturn(team);
        assertNotNull(teamService.update(team));
        verify(iTeamRepository,times(1)).save(team);
    }

    @Test
    void delete() {
        teamService.delete(team.getId());
        verify(iTeamRepository,times(1)).deleteById(team.getId());
    }

    @Test
    void listCyclistCodeTeam() {
        when(iTeamRepository.findByCodeTeam(team.getCodeTeam())).thenReturn(team);
        assertNotNull(teamService.listCyclistCodeTeam(team.getCodeTeam()));
    }
}