package com.client.bnb.myapplication.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Dmytro on 20.10.2018.
 */

public class Author implements Serializable {

    @SerializedName("AKey")
    @Expose
    private String AKey = "";

    @SerializedName("AName")
    @Expose
    private String AName = "";

    @SerializedName("APhone")
    @Expose
    private String APhone = "";

    @SerializedName("AEmail")
    @Expose
    private String AEmail = "";

    public String getAKey() { return AKey; }
    public void setAKey(String AKey) { this.AKey = AKey; }

    public String getAName() { return AName; }
    public void setAName(String AName) { this.AName = AName; }

    public String getAPhone() { return APhone; }
    public void setAPhone(String APhone) { this.APhone = APhone; }

    public String getAEmail() { return AEmail; }
    public void setAEmail(String AEmail) { this.AEmail = AEmail; }

}
