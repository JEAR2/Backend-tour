package com.sofka.tourFrance.Service;

import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Repository.ITeamRepository;
import com.sofka.tourFrance.Service.interfaces.ITeam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class TeamService implements ITeam {
    @Autowired
    private ITeamRepository iTeamRepository;
    @Override
    public List<Team> findAll() {
        return iTeamRepository.findAll();
    }

    @Override
    public Optional<Team> findById(Long id) {
        return iTeamRepository.findById(id);
    }

    @Override
    public Team save(Team team) {
        return iTeamRepository.save(team);
    }

    @Override
    public Team update(Long id, Team team) {
        return iTeamRepository.save(team);
    }

    @Override
    public void delete(Long id) {
        iTeamRepository.deleteById(id);
    }

    public Set<Cyclist> listCyclistCodeTeam(String code){
        Team team = iTeamRepository.findByCodeTeam(code);
        return team.getCyclistsList();
    }


}
