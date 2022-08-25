package com.sofka.tourFrance.Service;

import com.sofka.tourFrance.Domain.Country;
import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Repository.ICountryRepository;
import com.sofka.tourFrance.Service.interfaces.ICountry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CountryService implements ICountry {

    @Autowired
    private ICountryRepository countryRepository;

    @Override
    public List<Country> findAll() {
       return countryRepository.findAll();
    }

    @Override
    public Optional<Country> findById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country save(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Country update( Country country,Long id) {
        return countryRepository.save(country);
    }

    @Override
    public void delete(Long id) {
        countryRepository.deleteById(id);
    }

    public Set<Cyclist> getCyclistByCountry(Long id){
        Optional<Country> country = countryRepository.findById(id);
        return country.get().getCyclistList();
    }

    public Set<Team> getTeamsByCountry(Long id){
        Optional<Country> country = countryRepository.findById(id);
        return country.get().getTeamList();
    }
}
