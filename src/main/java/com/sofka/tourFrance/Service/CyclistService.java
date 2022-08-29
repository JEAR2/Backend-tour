package com.sofka.tourFrance.Service;

import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Repository.ICyclistRepository;
import com.sofka.tourFrance.Service.interfaces.ICyclist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CyclistService implements ICyclist {

    @Autowired
    private ICyclistRepository iCyclistRepository;

    @Override
    public List<Cyclist> findAll() {
        return iCyclistRepository.findAll();
    }

    @Override
    public Optional<Cyclist> findById(Long id) {
        return iCyclistRepository.findById(id);
    }

    @Override
    public Cyclist save(Cyclist cyclist) {
        return iCyclistRepository.save(cyclist);
    }

    @Override
    public Cyclist update(Cyclist cyclist) {
        return iCyclistRepository.save(cyclist);
    }

    @Override
    public void delete(Long id) {
        iCyclistRepository.deleteById(id);
    }
}
