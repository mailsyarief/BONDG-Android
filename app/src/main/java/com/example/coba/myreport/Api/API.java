package com.example.coba.myreport.Api;

import com.example.coba.myreport.Response.LoginResponse;
import com.example.coba.myreport.Response.TaskResponse;

import retrofit2.Call;
import retrofit2.http.GET;

public interface API {

    @GET("login.json")
    Call<LoginResponse> login();

    @GET("listtask.json")
    Call<TaskResponse> getTask();


}
