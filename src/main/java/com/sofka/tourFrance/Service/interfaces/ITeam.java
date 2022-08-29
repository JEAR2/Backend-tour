package com.sofka.tourFrance.Service.interfaces;

import com.sofka.tourFrance.Domain.Team;

import java.util.List;
import java.util.Optional;

public interface ITeam {
    List<Team> findAll();
    Optional<Team> findById(Long id);
    Team save(Team team);
    Team update(Team team);
    void delete(Long id);
}
