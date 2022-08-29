package com.sofka.tourFrance.Controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

@WebMvcTest(CountryController.class)
@Slf4j
class CountryControllerTest {


    @BeforeEach
    void setup(){
    }

    @Test
    void getCountries() throws Exception {

    }

    @Test
    void findCountryById() throws Exception {

    }


    @Test
    void saveCountry() throws Exception {

    }


    @Test
    void deleteCountry() throws Exception {
    }

    @Test
    void updateCountry() throws Exception {
    }

    @Test
    void getCyclistsByCountry() throws Exception {
    }

    @Test
    void getTeamsByCountry() throws Exception {
    }

    @Test
    void getTeamsByCountryCode() throws Exception {
    }
}