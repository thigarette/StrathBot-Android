package com.thiga.strathbot.api;

import com.thiga.strathbot.models.Result;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiService {

    @FormUrlEncoded
    @POST("login")
    Call<Result> userLogin(
            @Field("username") int username,
            @Field("password") String password
    );
}
