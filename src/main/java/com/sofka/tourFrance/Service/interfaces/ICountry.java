package com.sofka.tourFrance.Service.interfaces;

import com.sofka.tourFrance.Domain.Country;

import java.util.List;
import java.util.Optional;

public interface ICountry {
    List<Country> findAll();
    Optional<Country> findById(Long id);
    Country save(Country country);
    Country update(Country country,Long id);
    void delete(Long id);
}
