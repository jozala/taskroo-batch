package com.taskroo.batch.domain;

public class User {
    private final Object id;
    private final String email;

    public User(Object id, String email) {
        this.id = id;
        this.email = email;
    }

    public Object getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }
}
