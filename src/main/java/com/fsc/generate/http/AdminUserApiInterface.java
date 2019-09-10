package com.fsc.generate.http;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Map;

public interface AdminUserApiInterface {

    @GET("/api/admin/user/create/v1")
    public Call<Map<String, Object>> createUser(@Query("did") String did,
                                                @Query("vendor") String vendor,
                                                @Query("vendorId") long vendorId,
                                                @Query("batchNo") String batchNo,
                                                @Query("prepayedplan") int prepayedplan,
                                                @Query("locked") int locked,
                                                @Query("releaseId") String releaseId);


}
