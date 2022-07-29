package com.client.bnb.myapplication;


import com.client.bnb.myapplication.models.Sensor;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dmytro on 09.03.2017.
 */

public interface ManagerApi {

    @GET("api/getSensorByKey")
    Call<Sensor> getSensorByKey(@Query("sensorKey") String sensorKey);

}
