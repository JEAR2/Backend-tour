package com.sofka.tourFrance.Controller;

import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Domain.Team;
import com.sofka.tourFrance.Service.CyclistService;
import com.sofka.tourFrance.Service.TeamService;
import com.sofka.tourFrance.utility.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/cyclist")
public class CyclistController {
    Response response = new Response();
    @Autowired
    private CyclistService cyclistService;

    @Autowired
    private TeamService teamService;

    @GetMapping(value = "/all")
    public List<Cyclist> getCyclist(){
        return cyclistService.findAll();
    }

    @PostMapping(path = "/save")
    public ResponseEntity<Response> saveCyclist(@RequestBody Cyclist cyclist){

        Long idTeam = cyclist.getTeam().getId();
        Optional<Team> team = teamService.findById(idTeam);
        try {
            if (team.isPresent() && team.get().getCyclistsList().size() < 8) {
                response.data = cyclistService.save(cyclist);
                response.message = "Ciclista Almacenado Correctamente";

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
            response.message = "El equipo no existe o el equipo ya tiene la cantidad de cilcistas completo (8)";
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }catch (Exception e){
            response.message = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

    }

    @GetMapping(path = "/find/{id}")
    public Optional<Cyclist> findCyclistById(@PathVariable("id") Long id){
        return cyclistService.findById(id);
    }

    @PutMapping(value = "/update/{id}")
    public ResponseEntity<Cyclist> updateCyclist(@RequestBody Cyclist cyclist,@PathVariable("id") Long id ){

        return new ResponseEntity<Cyclist>(cyclistService.save(cyclist), HttpStatus.OK);
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity<Void> deleteCyclist(@PathVariable Long id){
        Optional cyclist = null;
        cyclist = cyclistService.findById(id);
        if(cyclist.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        cyclistService.delete(id);
        return ResponseEntity.ok().build();
    }

}
