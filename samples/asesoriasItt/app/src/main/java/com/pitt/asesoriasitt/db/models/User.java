package com.pitt.asesoriasitt.db.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "user")
public class User implements Serializable {

    private long id;

    @ColumnInfo(name = "full_name")
    @SerializedName("full_name")
    private String fullName;

    private String username;

    private String password;

    @ColumnInfo(name = "fk_id_carrier")
    @SerializedName("fk_id_carrier")
    private long fkIdCarrier;

    @ColumnInfo(name = "fk_id_rol")
    @SerializedName("fk_id_rol")
    private long fkIdRol;

    @ColumnInfo(name = "carrier_name")
    @SerializedName("carrier_name")
    private String carrierName;

    private Contact contact;

    private Carrier carrier;

    private ArrayList<Advisory> advisories;


    public Contact getContact() {
        return contact;
    }

    public void setContact(Contact contact) {
        this.contact = contact;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public long getFkIdCarrier() {
        return fkIdCarrier;
    }

    public void setFkIdCarrier(long fkIdCarrier) {
        this.fkIdCarrier = fkIdCarrier;
    }

    public long getFkIdRol() {
        return fkIdRol;
    }

    public void setFkIdRol(long fkIdRol) {
        this.fkIdRol = fkIdRol;
    }

    public String getCarrierName() {
        return carrierName;
    }

    public void setCarrierName(String carrierName) {
        this.carrierName = carrierName;
    }

    public Carrier getCarrier() {
        return carrier;
    }

    public void setCarrier(Carrier carrier) {
        this.carrier = carrier;
    }

    public ArrayList<Advisory> getAdvisories() {
        return advisories;
    }

    public void setAdvisories(ArrayList<Advisory> advisories) {
        this.advisories = advisories;
    }

}
