package com.maik.greenhouse.models;

import android.util.ArrayMap;

import java.util.ArrayList;
import java.util.Map;


public class GreenHouse {
    private String name;
    private String description;
    private Long limit;

    private ArrayList<String> img;
    private Map<String, String> average;

    private Map<String, String> municipios = new ArrayMap<>();


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

    public Long getLimit() {
        return limit;
    }

    public void setLimit(Long limit) {
        this.limit = limit;
    }

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public Map<String, String> getAverage() {
        return average;
    }

    public void setAverage(Map<String, String> average) {
        this.average = average;
    }
}
