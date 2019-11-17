package com.maik.greenhouse.Models;

import java.util.ArrayList;


public class GreenHouse {
    public String name;
    public ArrayList<String> img;

    public ArrayList<String> getImg() {
        return img;
    }

    public void setImg(ArrayList<String> img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
