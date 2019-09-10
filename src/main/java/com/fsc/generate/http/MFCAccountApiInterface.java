package com.fsc.generate.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Map;

public interface MFCAccountApiInterface {

    @GET("/api/user3/register/v1")
    public Call<Map<String, Object>> createUser(@Query("email") String email,
            @Query("password") String password,
            @Query("userName") String userName,
            @Query("endUserIp") String endUserIp);


}
