package com.client.bnb.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dmytro on 21.10.2018.
 */

public class Location implements Serializable {

    @SerializedName("LKey")
    @Expose
    private String LKey = "";

    @SerializedName("LCity")
    @Expose
    private String LCity = "";

    @SerializedName("LCountry")
    @Expose
    private String LCountry = "";

    @SerializedName("LFromMap")
    @Expose
    private String LFromMap = "";

    @SerializedName("LCoordFromMap")
    @Expose
    private String LCoordFromMap = "";

    public String getLKey() { return LKey; }
    public void setLKey(String LKey) { this.LKey = LKey; }

    public String getLCity() { return LCity; }
    public void setLCity(String LCity) { this.LCity = LCity; }

    public String getLCountry() { return LCountry; }
    public void setLCountry(String LCountry) { this.LCountry = LCountry; }

    public String getLFromMap() { return LFromMap; }
    public void setLFromMap(String LFromMap) { this.LFromMap = LFromMap; }

    public String getLCoordFromMap() { return LCoordFromMap; }
    public void setLCoordFromMap(String LCoordFromMap) { this.LCoordFromMap = LCoordFromMap; }

}
