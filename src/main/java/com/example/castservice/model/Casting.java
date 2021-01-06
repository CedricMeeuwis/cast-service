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
    private String startDate;
    private String endDate;

    public Casting() {
    }

    public Casting(String id, String castMember, Integer movieId, String startDate, String endDate) {
        this.id = id;
        this.castMember = castMember;
        this.movieId = movieId;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
