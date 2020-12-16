package com.example.castservice.repository;

import com.example.castservice.model.Casting;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CastingRepository extends MongoRepository<Casting, String>{
    Casting findCastingById(String id);
    List<Casting> findCastingByMovieId(Integer movieid);
    List<Casting> findCastingByCastMemberId(Integer castmemberid);
}
