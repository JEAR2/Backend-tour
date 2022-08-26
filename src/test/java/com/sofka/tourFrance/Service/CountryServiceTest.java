package com.sofka.tourFrance.Service;

import com.sofka.tourFrance.Domain.Country;
import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Repository.ICountryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class CountryServiceTest {

    @Mock
    private ICountryRepository iCountryRepository;

    @InjectMocks
    private CountryService countryService;

    private Country country;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        country = new Country();
        country.setId(1L);
        country.setName("Colombia");
        country.setCodeCountry("CO");
        country.setCyclistList(new HashSet<>());
        country.setTeamList(new HashSet<>());
    }

    @Test
    void findAll() {

        when(iCountryRepository.findAll()).thenReturn(Arrays.asList(country));
        assertNotNull(countryService.findAll());
    }

    @Test
    void findById() {
        when(iCountryRepository.findById(country.getId())).thenReturn(Optional.ofNullable(country));
        assertNotNull(countryService.findById(country.getId()));
    }

    @Test
    void save() {
        when(iCountryRepository.save(country)).thenReturn(country);
        assertNotNull(countryService.save(country));
        verify(iCountryRepository,times(1)).save(country);
    }

    @Test
    void update() {
        when(iCountryRepository.save(country)).thenReturn(country);
        assertNotNull(countryService.update(country));
        verify(iCountryRepository,times(1)).save(country);
    }


    @Test
    void delete() {
        countryService.delete(country.getId());
        verify(iCountryRepository,times(1)).deleteById(country.getId());
    }

    @Test
    void testGetCyclistByCountry() {
        Cyclist cyclist = new Cyclist();
        Set<Cyclist> cyclistList= new HashSet<>();
        cyclist.setId(1L);
        cyclist.setName("John");
        cyclist.setCompetitorNumber("JO1");

        Cyclist cyclist2 = new Cyclist();
        cyclist2.setId(2L);
        cyclist2.setName("Edward");
        cyclist2.setCompetitorNumber("ED1");

        cyclistList.add(cyclist);
        cyclistList.add(cyclist2);
        country.setCyclistList(cyclistList);
        when(iCountryRepository.findById(country.getId())).thenReturn(Optional.ofNullable(country));
        assertNotNull(countryService.getCyclistByCountry(country.getId()));
        assertEquals(2,countryService.getCyclistByCountry(country.getId()).size());
    }

    @Test
    void getTeamsByCountry() {
        Team team = new Team();
        Set<Team> teamSet = new HashSet<>();

        team.setId(1L);
        team.setName("Equipo1");
        team.setCodeTeam("CE1");
        team.setCountry(country);
        team.setCyclistsList(new HashSet<>());
        teamSet.add(team);
        country.setTeamList(teamSet);
        when(iCountryRepository.findById(country.getId())).thenReturn(Optional.ofNullable(country));
        assertNotNull(countryService.getTeamsByCountry(country.getId()));
        assertEquals(1,countryService.getTeamsByCountry(country.getId()).size());

    }

    @Test
    void getTeamsByCountryCode() {

        Team team = new Team();
        Set<Team> teamSet = new HashSet<>();

        team.setId(1L);
        team.setName("Equipo1");
        team.setCodeTeam("CE1");
        team.setCountry(country);
        team.setCyclistsList(new HashSet<>());
        teamSet.add(team);
        country.setTeamList(teamSet);
        when(iCountryRepository.findByCodeCountry(country.getCodeCountry())).thenReturn(Optional.ofNullable(country));
        assertNotNull(countryService.getTeamsByCountryCode(country.getCodeCountry()));
        assertEquals(1,countryService.getTeamsByCountryCode(country.getCodeCountry()).size());
    }
}