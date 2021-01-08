package com.example.castservice.controller;

import com.example.castservice.model.Casting;
import com.example.castservice.repository.CastingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CastingController {
    @Autowired
    private CastingRepository castingRepository;

    @PostMapping("/casting")
    public Casting addCasting(@RequestBody Casting casting){
        castingRepository.save(casting);
        return casting;
    }
    @PutMapping("/casting")
    public Casting updateCasting(@RequestBody Casting updatedCasting){
        Casting retrievedCasting = castingRepository.findCastingById(updatedCasting.getId());
        //value changes
        retrievedCasting.setCastMember(updatedCasting.getCastMember());
        retrievedCasting.setMovieId(updatedCasting.getMovieId());

        castingRepository.save(retrievedCasting);
        return retrievedCasting;
    }
    @DeleteMapping("/casting/{id}")
    public ResponseEntity deleteRole(@PathVariable String id){
        Casting casting = castingRepository.findCastingById(id);
        if(casting != null){
            castingRepository.delete(casting);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
    //gets
    @GetMapping("/casting/movie/{movieId}")
    public List<Casting> getCastingsByMovie(@PathVariable Integer movieId){
        return castingRepository.findCastingByMovieId(movieId);
    }
    @GetMapping("/casting/castmember/{castMember}")
    public List<Casting> getCastingsByCastMember(@PathVariable String castMember){
        return castingRepository.findCastingByCastMember(castMember);
    }
}
