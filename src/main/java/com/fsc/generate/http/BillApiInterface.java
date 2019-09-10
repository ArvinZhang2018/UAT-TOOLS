package com.fsc.generate.http;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.springframework.http.ResponseEntity;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface BillApiInterface {

    @POST("/integration/shareit/v1")
    public Call<ResponseBody> sendNotification(@Body RequestBody body);


}
