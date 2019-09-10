package com.fsc.generate.http;

import retrofit2.Call;
import retrofit2.http.*;

import java.util.Map;

public interface AuthApiInterface {

    @Headers("Connection:close")
    @FormUrlEncoded
    @POST("/api/uc/login4app/v1")
    public Call<Map<String, Object>> accountLogin(
            @FieldMap Map<String,String> fields);

    @FormUrlEncoded
    @POST("/api/admin/user/create/v1")
    public Call<Map<String, Object>> accountLogin(
            @Field("st") String st,
            @Field("password") String password,
            @Field("sdk") String sdk,
            @Field("clientType") String clientType,
            @Field("e_mac") String e_mac,
            @Field("fingerprint") String fingerprint,
            @Field("region") String region,
            @Field("did") String did,
            @Field("cpuId") String cpuId,
            @Field("email") String email,
            @Field("loginId") String loginId,
            @Field("di") String di,
            @Field("imeiId") String imeiId,
            @Field("w_mac") String w_mac,
            @Field("androidId") String androidId,
            @Field("display") String display,
            @Field("accountLoginMethod") String accountLoginMethod,
            @Field("uid") String uid,
            @Field("appVer") String appVer,
            @Field("language") String language,
            @Field("manufacturer") String manufacturer,
            @Field("subAccountId") String subAccountId,
            @Field("channel") String channel,
            @Field("b_mac") String b_mac);

}
