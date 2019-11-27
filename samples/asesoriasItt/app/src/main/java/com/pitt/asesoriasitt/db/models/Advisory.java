package com.pitt.asesoriasitt.db.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Advisory implements Serializable {
    private long id;
    private String name;
    private String description;
    private User user;
    @SerializedName("place_name")
    private String placeName;

    @SerializedName("fk_id_user")
    private long fkIdUser;

    private ArrayList<Day> days;

    public String getPlaceName() {
        return placeName;
    }

    public void setPlaceName(String placeName) {
        this.placeName = placeName;
    }

    public ArrayList<Day> getDays() {
        return days;
    }

    public void setDays(ArrayList<Day> days) {
        this.days = days;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getFkIdUser() {
        return fkIdUser;
    }

    public void setFkIdUser(long fkIdUser) {
        this.fkIdUser = fkIdUser;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
