package com.sofka.tourFrance.Controller;

import com.sofka.tourFrance.Domain.Country;
import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Service.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping("/api/country")
public class CountryController {
    @Autowired
    private CountryService countryService;

    @GetMapping(value = "/all")
    public List<Country> getCountries(){
        return countryService.findAll();
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Country> saveCountry(@RequestBody Country country){
        Country countryNSave =  countryService.save(country);
        return new ResponseEntity<>(countryNSave, HttpStatus.OK);
    }

//    @PostMapping(value = "/save")
//    public Country saveCountry(@RequestBody Country country){
//        return countryService.save(country);
//
//    }

    @GetMapping(path = "/find/{id}")
    public Optional<Country> findCountryById(@PathVariable("id") Long id){

        return countryService.findById(id);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Country> updateCountry(@RequestBody Country country,@PathVariable("id") Long id ){

        return new ResponseEntity<Country>(countryService.update(country), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteCountry(@PathVariable Long id){
        Optional country = null;
        country = countryService.findById(id);
        if(country.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        countryService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/find/cyclist/{id}")
    public Set<Cyclist> getCyclistsByCountry(@PathVariable Long id){
        return countryService.getCyclistByCountry(id);
    }

    @GetMapping(value = "/find/teams/{id}")
    public Set<Team> getTeamsByCountry(@PathVariable Long id){
        return countryService.getTeamsByCountry(id);
    }

    @GetMapping(value = "/find/teams/code/{code}")
    public Set<Team> getTeamsByCountryCode(@PathVariable String code){
        return countryService.getTeamsByCountryCode(code);
    }


}
