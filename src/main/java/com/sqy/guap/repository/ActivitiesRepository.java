package com.sqy.guap.repository;


import org.springframework.stereotype.Repository;

@Repository
public class ActivitiesRepository {
    private final static String SQL_SELECT_BY_ID = "select name, age, weight, height, id, gender from person where id = :id";

    public void getLatest() {

    }
}
