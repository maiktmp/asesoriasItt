package com.pitt.asesoriasitt.db.models;

import java.io.Serializable;

public class Contact implements Serializable {

    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
