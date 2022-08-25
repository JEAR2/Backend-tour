package com.sofka.tourFrance.Controller;

import com.sofka.tourFrance.Domain.Cyclist;
import com.sofka.tourFrance.Service.CyclistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@RequestMapping("/api/cyclist")
public class CyclistController {

    @Autowired
    private CyclistService cyclistService;

    @GetMapping(value = "/all")
    public List<Cyclist> getCyclist(){
        return cyclistService.findAll();
    }

    @PostMapping(value = "/save")
    public ResponseEntity<Cyclist> saveCyclist(@RequestBody Cyclist cyclist){
        Cyclist cyclistSave = cyclistService.save(cyclist);
        return new ResponseEntity<>(cyclistSave, HttpStatus.OK);
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
