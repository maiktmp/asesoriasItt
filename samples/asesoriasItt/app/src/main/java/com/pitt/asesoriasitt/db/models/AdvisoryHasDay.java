package com.pitt.asesoriasitt.db.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class AdvisoryHasDay  implements Serializable {

    private long id;
    @SerializedName("start_hour")
    private String startHour;

    @SerializedName("end_hour")
    private String endHour;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getStartHour() {
        return startHour;
    }

    public void setStartHour(String startHour) {
        this.startHour = startHour;
    }

    public String getEndHour() {
        return endHour;
    }

    public void setEndHour(String endHour) {
        this.endHour = endHour;
    }
}
