package com.example.castservice.controller;

import com.example.castservice.model.CastMember;
import com.example.castservice.repository.CastMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CastMemberController {
    @Autowired
    private CastMemberRepository castMemberRepository;

    @GetMapping("castmember/role/{roleId}")
    public List<CastMember> getCastMembersByRole(@PathVariable Integer roleId){
        return castMemberRepository.findCastMemberByRoleId(roleId);
    }
    @GetMapping("castmember/name/{name}")
    public CastMember getCastMemberByName(@PathVariable String name){
        return castMemberRepository.findCastMemberByName(name);
    }
    @GetMapping("castmember/nationality/{nationality}")
    public List<CastMember> getCastMembersByNationality(@PathVariable String nationality){
        return castMemberRepository.findCastMemberByNationality(nationality);
    }
    @PostMapping("/castmember")
    public CastMember addCastMember(@RequestBody CastMember castMember){
        castMemberRepository.save(castMember);
        return castMember;
    }
    @PutMapping("/castmember")
    public CastMember updateCastMember(@RequestBody CastMember updatedCastMember){
        CastMember retrievedCastMember = castMemberRepository.findCastMemberById(updatedCastMember.getId());
        //value changes
        retrievedCastMember.setName(updatedCastMember.getName());
        retrievedCastMember.setBirthDate(updatedCastMember.getBirthDate());
        retrievedCastMember.setNationality(updatedCastMember.getNationality());
        retrievedCastMember.setRoleId(updatedCastMember.getRoleId());

        castMemberRepository.save(retrievedCastMember);
        return retrievedCastMember;
    }
    @DeleteMapping("/castmember/{id}")
    public ResponseEntity deleteCastMember(@PathVariable String id){
        CastMember castMember = castMemberRepository.findCastMemberById(id);
        if(castMember != null){
            castMemberRepository.delete(castMember);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
