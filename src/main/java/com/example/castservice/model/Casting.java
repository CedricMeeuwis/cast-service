package com.example.castservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "castings")
public class Casting {
    @Id
    private String id;
    private String castMember;
    private Integer movieId;

    public Casting() {
    }

    public Casting(String id, String castMember, Integer movieId) {
        this.id = id;
        this.castMember = castMember;
        this.movieId = movieId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCastMember() {
        return castMember;
    }

    public void setCastMember(String castMember) {
        this.castMember = castMember;
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }
}
