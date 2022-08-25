package com.sofka.tourFrance.Repository;

import com.sofka.tourFrance.Domain.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICountryRepository extends JpaRepository<Country,Long> {

}
