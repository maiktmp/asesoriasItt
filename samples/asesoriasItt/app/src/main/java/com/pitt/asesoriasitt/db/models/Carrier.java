package com.pitt.asesoriasitt.db.models;

import androidx.room.Entity;

import java.io.Serializable;

@Entity(tableName = "carrier")
public class Carrier implements Serializable {

    private long id;
    private String name;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
