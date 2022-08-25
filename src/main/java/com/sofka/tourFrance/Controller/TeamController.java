package com.sofka.tourFrance.Controller;

import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@CrossOrigin
@RestController
@RequestMapping(value = "/api/team")
public class TeamController {
    @Autowired
    private TeamService teamService;

    @GetMapping(value = "/all")
    public List<Team> findAll(){
        return teamService.findAll();
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Team> saveTeam(@RequestBody Team team){
        Team teamSave =  teamService.save(team);
        return new ResponseEntity<Team>(teamSave, HttpStatus.OK);
    }

    @GetMapping(path = "/find/{id}")
    public Optional<Team> findTeamById(@PathVariable("id") Long id){
        return teamService.findById(id);
    }

    @PutMapping(path = "/update/{id}")
    public ResponseEntity<Team> updateTeam(@RequestBody Team team, @PathVariable("id") Long id){
        return new ResponseEntity<Team>(teamService.save(team),HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteTeam(@PathVariable Long id){
        Optional team = null;
        team = teamService.findById(id);
        if(team.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        teamService.delete(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/findCyclists/{code}")
    public Set<Cyclist> findCyclist(@PathVariable("code") String code){
        return teamService.listCyclistCodeTeam(code);
    }


}
