package com.sqy.guap.domain;

public class User {
    private final String username;
    private final long id;

    public User(String username, long id) {
        this.username = username;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public long getId() {
        return id;
    }
}
