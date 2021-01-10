package com.example.castservice.controller;

import com.example.castservice.model.CastMember;
import com.example.castservice.repository.CastMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.List;

@RestController
public class CastMemberController {
    @Autowired
    private CastMemberRepository castMemberRepository;

    @PostConstruct
    public void fillDB() {
        if (castMemberRepository.count() == 0) {
            castMemberRepository.save(new CastMember("1","Kopperman", ("11/11/2020"), "Belgisch", "Directeur"));
            castMemberRepository.save(new CastMember("2","John", ("01/11/1985"), "Nederlands", "Acteur"));
            castMemberRepository.save(new CastMember("3","Joe", ("05/11/1990"), "Belgisch", "Kok"));
            castMemberRepository.save(new CastMember("5","Bob", ("06/11/1962"), "Belgisch", "Stuntman"));
        }
    }

    @GetMapping("castmember/role/{role}")
    public List<CastMember> getCastMembersByRole(@PathVariable String role){
        return castMemberRepository.findCastMemberByRole(role);
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
        retrievedCastMember.setRole(updatedCastMember.getRole());

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
