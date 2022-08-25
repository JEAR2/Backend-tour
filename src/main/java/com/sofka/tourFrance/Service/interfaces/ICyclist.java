package com.sofka.tourFrance.Service.interfaces;

import com.sofka.tourFrance.Domain.Cyclist;

import java.util.List;
import java.util.Optional;

public interface ICyclist {
    List<Cyclist> findAll();
    Optional<Cyclist> findById(Long id);
    Cyclist save(Cyclist cyclist);
    Cyclist update(Long id, Cyclist cyclist);
    void delete(Long id);
}
