package com.sofka.tourFrance.Repository;

import com.sofka.tourFrance.Domain.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ITeamRepository extends JpaRepository<Team,Long> {
    Team findByCodeTeam(String code);
}
