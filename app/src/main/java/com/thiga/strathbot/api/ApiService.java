package com.thiga.strathbot.api;

import com.google.gson.JsonObject;
import com.thiga.strathbot.models.Message;
import com.thiga.strathbot.models.Result;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    @Headers("Content-type: application/json")
    @POST("login")
    Call<ResponseBody> userLogin(
            @Body JsonObject body
    );

    @Headers("Content-type: application/json")
    @POST("/")
    Call<ResponseBody> sendMessage(
            @Body JsonObject body
    );

    @GET("/")
    Call<JsonObject> getMessage();
}
