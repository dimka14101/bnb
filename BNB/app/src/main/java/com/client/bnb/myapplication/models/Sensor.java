package com.client.bnb.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dmytro on 02.01.2017.
 */

public class Sensor implements Serializable {

    @SerializedName("SKey")
    @Expose
    private String SKey = "";

    @SerializedName("SName")
    @Expose
    private String SName = "";

    @SerializedName("SDetails")
    @Expose
    private String SDetails = "";

    @SerializedName("SText")
    @Expose
    private String SText = "";

    @SerializedName("SCreateDate")
    @Expose
    private String SCreateDate = "";

    @SerializedName("SLastChangeDate")
    @Expose
    private String SLastChangeDate = "2017-01-10T10:10:10.10";

    @SerializedName("Author")
    @Expose
    private Author Author = null;

    @SerializedName("Location")
    @Expose
    private Location Location = null;


    public String getSKey() { return SKey; }
    public void setSKey(String SKey) { this.SKey = SKey; }

    public String getSName() { return SName; }
    public void setSName(String SName) { this.SName = SName; }

    public String getSDetails() { return SDetails; }
    public void setSDetails(String SDetails) { this.SDetails = SDetails; }

    public String getSText() { return SText; }
    public void setSText(String SText) { this.SText = SText; }

    public String getSCreateDate() { return SCreateDate; }
    public void setSCreateDate(String SCreateDate) { this.SCreateDate = SCreateDate; }

    public String getSLastChangeDate() { return SLastChangeDate; }
    public void setSLastChangeDate(String SLastChangeDate) { this.SLastChangeDate = SLastChangeDate; }

    public Author getAuthor() { return Author; }
    public void setAuthor(Author author) { Author = author; }

    public Location getLocation() { return Location; }
    public void setLocation(Location location) { Location = location; }

}
