package com.fsc.generate.http;

import com.fsc.generate.model.dto.SubscriptionItemsJSon;
import com.fsc.generate.utils.AccountCardInfoDo;
import com.valor.mfc.vms.api.model.common.response.ResponseMsgSingle;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.Map;

public interface ShareItApiInterface {

    @GET("/api/v1/vendors/{vendorId}/subscriptions/{id}/items")
    public Call<ResponseBody> querySubscriptionItems(@Path("vendorId") String vendorId, @Path("id") String subscriptionId);

}
