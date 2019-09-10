package com.fsc.generate.http;

import com.fsc.generate.utils.AccountCardInfoDo;
import com.fsc.generate.utils.RechargeCardResp;
import com.valor.mfc.vms.api.model.common.response.ResponseMsgSingle;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.util.Map;

public interface CCApiInterface {

    @GET("/api/charge/activation/create/v1")
    public Call<Map<String, Object>> createChargeCard(@Query("bizSysId") String bizSysId,
            @Query("batchNo") String batchNo,
            @Query("chargeType") String chargeType,
            @Query("bizType") String bizType,
            @Query("vendor") String vendor,
            @Query("bizSysUid") String bizSysUid,
            @Query("autoActive") String autoActive,
            @Query("bizValue") String bizValue);

    @GET("/api/admin/accountcard/create/v1")
    public Call<ResponseMsgSingle<AccountCardInfoDo>> createAccountCard(@Query("systemId") int systemId,
            @Query("batchId") String batchId,
            @Query("sellerId") String sellerId,
            @Query("serviceTime") int serviceTime,
            @Query("serviceTimeUnit") char serviceTimeUnit,
            @Query("locked") int locked,
            @Query("remarks") String remarks,
            @Query("planId") long planId);

    @GET("/api/charge/recharge/create/v2")
    public Call<ResponseMsgSingle<RechargeCardResp>> createRechargeCard(@Query("bisSysType") int bisSysType,
            @Query("chargeType") String chargeType,
            @Query("bizType") String bizType,
            @Query("bizValue") int bizValue,
            @Query("expireTs") long expireTs,
            @Query("sellers") String sellers,
            @Query("batchNo") String batchNo,
            @Query("locked") int locked,
            @Query("plan") int plan);


}
