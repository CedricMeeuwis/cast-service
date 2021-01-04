package com.example.castservice.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "castings")
public class Casting {
    @Id
    private String id;
    private Integer castMemberId;
    private Integer movieId;
    private String startDate;
    private String endDate;

    public Casting() {
    }

    public Casting(String id, Integer castMemberId, Integer movieId, String startDate, String endDate) {
        this.id = id;
        this.castMemberId = castMemberId;
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

    public Integer getCastMemberId() {
        return castMemberId;
    }

    public void setCastMemberId(Integer castMemberId) {
        this.castMemberId = castMemberId;
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
