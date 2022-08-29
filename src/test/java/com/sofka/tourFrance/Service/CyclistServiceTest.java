package com.sofka.tourFrance.Service;

import com.sofka.tourFrance.Domain.Country;
import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Repository.ICyclistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CyclistServiceTest {

    @Mock
    private ICyclistRepository iCyclistRepository;


    @InjectMocks
    private CyclistService cyclistService;

    private Cyclist cyclist;

    @BeforeEach
    void setup(){
        MockitoAnnotations.openMocks(this);
        cyclist = new Cyclist();
        cyclist.setId(1L);
        cyclist.setName("John");
        cyclist.setCompetitorNumber("JO1");
        cyclist.setTeam(new Team());
        cyclist.setCountry(new Country());

    }

    @Test
    void findAll() {
        when(iCyclistRepository.findAll()).thenReturn(Arrays.asList(cyclist));
        assertNotNull(cyclistService.findAll());
        verify(iCyclistRepository,times(1)).findAll();
    }

    @Test
    void findById() {
        when(iCyclistRepository.findById(cyclist.getId())).thenReturn(Optional.ofNullable(cyclist));
        assertNotNull(cyclistService.findById(cyclist.getId()));
        verify(iCyclistRepository,times(1)).findById(cyclist.getId());
    }

    @Test
    void save() {
        when(iCyclistRepository.save(cyclist)).thenReturn(cyclist);
        assertNotNull(cyclistService.save(cyclist));
        verify(iCyclistRepository,times(1)).save(cyclist);
    }

    @Test
    void update() {
        when(iCyclistRepository.save(cyclist)).thenReturn(cyclist);
        assertNotNull(cyclistService.update(cyclist));
        verify(iCyclistRepository,times(1)).save(cyclist);
    }

    @Test
    void delete() {
        cyclistService.delete(cyclist.getId());
        verify(iCyclistRepository,times(1)).deleteById(cyclist.getId());
    }
}