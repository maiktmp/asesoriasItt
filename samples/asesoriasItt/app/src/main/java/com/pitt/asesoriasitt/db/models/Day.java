package com.pitt.asesoriasitt.db.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Day implements Serializable {

    public Day() {

    }

    public Day(long id, String name) {
        this.id = id;
        this.name = name;
    }

    private long id;

    private String name;

    @SerializedName("pivot")
    private AdvisoryHasDay advisoryHasDay;

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

    public AdvisoryHasDay getAdvisoryHasDay() {
        if (advisoryHasDay == null) {
            advisoryHasDay = new AdvisoryHasDay();
        }
        return advisoryHasDay;
    }

    public void setAdvisoryHasDay(AdvisoryHasDay advisoryHasDay) {
        this.advisoryHasDay = advisoryHasDay;
    }

    public static ArrayList<Day> days() {
        return new ArrayList<Day>() {{
            add(new Day(1, "Lunes"));
            add(new Day(2, "Martes"));
            add(new Day(3, "Miercoles"));
            add(new Day(4, "Jueves"));
            add(new Day(5, "Viernes"));
        }};
    }
}
