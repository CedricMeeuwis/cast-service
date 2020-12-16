package com.example.castservice.controller;

import com.example.castservice.model.Casting;
import com.example.castservice.model.Role;
import com.example.castservice.repository.CastingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CastingController {
    @Autowired
    private CastingRepository castingRepository;

    //Simple CRUD
    @GetMapping("/casting/{id}")
    public Casting getRolesById(@PathVariable String id){
        return castingRepository.findCastingById(id);
    }
    @PostMapping("/casting")
    public Casting addCasting(@RequestBody Casting casting){
        castingRepository.save(casting);
        return casting;
    }
    @PutMapping("/casting")
    public Casting updateCasting(@RequestBody Casting updatedCasting){
        Casting retrievedCasting = castingRepository.findCastingById(updatedCasting.getId());
        //value changes
        retrievedCasting.setEndDate(updatedCasting.getEndDate());
        retrievedCasting.setStartDate(updatedCasting.getStartDate());
        retrievedCasting.setCastMemberId(updatedCasting.getCastMemberId());
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
    //other gets
    @GetMapping("/casting/movie/{movieId}")
    public List<Casting> getCastingsByMovie(@PathVariable Integer movieId){
        return castingRepository.findCastingByMovieId(movieId);
    }
    @GetMapping("/casting/castmember/{castMemberId}")
    public List<Casting> getCastingsByCastMember(@PathVariable Integer castMemberId){
        return castingRepository.findCastingByCastMemberId(castMemberId);
    }
}
