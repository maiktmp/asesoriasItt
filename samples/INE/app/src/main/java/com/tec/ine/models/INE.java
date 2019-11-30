package com.tec.ine.models;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class INE implements Serializable {

    private String birthDate;
    private String colony;
    private String curp;
    private String emissionYear;
    private String expiredYear;
    private String extNum;
    private String firstLastName;
    private String img;
    private String ineNumber;
    private String intNum;
    private String locality;
    private String municipality;
    private String name;
    private String registerYear;
    private String secondLastName;
    private String section;
    private String street;
    private String version;
    private String zipCode;
    private String gender;
    private String fullName;
    private String FBID;
    private Boolean active = true;


    public Map<String, Object> toMap() {
        Map<String, Object> data = new HashMap<>();
        data.put("birthDate", birthDate);
        data.put("colony", colony);
        data.put("curp", curp);
        data.put("emissionYear", emissionYear);
        data.put("expiredYear", expiredYear);
        data.put("extNum", extNum);
        data.put("firstLastName", firstLastName);
        data.put("img", img);
        data.put("ineNumber", ineNumber);
        data.put("intNum", intNum);
        data.put("locality", locality);
        data.put("municipality", municipality);
        data.put("name", name);
        data.put("registerYear", registerYear);
        data.put("secondLastName", secondLastName);
        data.put("section", section);
        data.put("street", street);
        data.put("version", version);
        data.put("zipCode", zipCode);
        data.put("gender", gender);
        data.put("fullName", fullName);
        data.put("active", active);

        return data;
    }

    public static INE toINE(Map<String, Object> map) {
        INE ine = new INE();
        ine.setBirthDate(String.valueOf(map.get("birthDate")));
        ine.setColony(String.valueOf(map.get("colony")));
        ine.setCurp(String.valueOf(map.get("curp")));
        ine.setEmissionYear(String.valueOf(map.get("emissionYear")));
        ine.setExpiredYear(String.valueOf(map.get("expiredYear")));
        ine.setExtNum(String.valueOf(map.get("extNum")));
        ine.setFirstLastName(String.valueOf(map.get("firstLastName")));
        ine.setImg(String.valueOf(map.get("img")));
        ine.setIneNumber(String.valueOf(map.get("ineNumber")));
        ine.setIntNum(((String) (map.get("intNum"))));
        ine.setLocality(String.valueOf(map.get("locality")));
        ine.setMunicipality(String.valueOf(map.get("municipality")));
        ine.setName(String.valueOf(map.get("name")));
        ine.setRegisterYear(String.valueOf(map.get("registerYear")));
        ine.setSecondLastName(String.valueOf(map.get("secondLastName")));
        ine.setSection(String.valueOf(map.get("section")));
        ine.setStreet(String.valueOf(map.get("street")));
        ine.setVersion(String.valueOf(map.get("version")));
        ine.setZipCode(String.valueOf(map.get("zipCode")));
        ine.setGender(String.valueOf(map.get("gender")));
        ine.setFullName(String.valueOf(map.get("fullName")));
        ine.setFBID(String.valueOf(map.get("FB_ID")));
        ine.setActive(((Boolean) map.get("active")));
        return ine;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getColony() {
        return colony;
    }

    public void setColony(String colony) {
        this.colony = colony;
    }

    public String getCurp() {
        return curp;
    }

    public void setCurp(String curp) {
        this.curp = curp;
    }

    public String getEmissionYear() {
        return emissionYear;
    }

    public void setEmissionYear(String emissionYear) {
        this.emissionYear = emissionYear;
    }

    public String getExpiredYear() {
        return expiredYear;
    }

    public void setExpiredYear(String expiredYear) {
        this.expiredYear = expiredYear;
    }

    public String getExtNum() {
        return extNum;
    }

    public void setExtNum(String extNum) {
        this.extNum = extNum;
    }

    public String getFirstLastName() {
        return firstLastName;
    }

    public void setFirstLastName(String firstLastName) {
        this.firstLastName = firstLastName;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getIneNumber() {
        return ineNumber;
    }

    public void setIneNumber(String ineNumber) {
        this.ineNumber = ineNumber;
    }

    public String getIntNum() {
        return intNum;
    }

    public void setIntNum(String intNum) {
        this.intNum = intNum;
    }

    public String getLocality() {
        return locality;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegisterYear() {
        return registerYear;
    }

    public void setRegisterYear(String registerYear) {
        this.registerYear = registerYear;
    }

    public String getSecondLastName() {
        return secondLastName;
    }

    public void setSecondLastName(String secondLastName) {
        this.secondLastName = secondLastName;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getVersion() {
        if (version == null) return "0";
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }


    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }


    public String getFBID() {
        return FBID;
    }

    public void setFBID(String FBID) {
        this.FBID = FBID;
    }


    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
