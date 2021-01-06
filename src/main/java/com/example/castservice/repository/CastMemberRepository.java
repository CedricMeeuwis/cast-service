package com.example.castservice.repository;

import com.example.castservice.model.CastMember;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastMemberRepository extends MongoRepository<CastMember, String>{
    CastMember findCastMemberById(String id);
    List<CastMember> findCastMemberByRole(String role);
    List<CastMember> findCastMemberByNationality(String nationality);
    CastMember findCastMemberByName(String name);
}
