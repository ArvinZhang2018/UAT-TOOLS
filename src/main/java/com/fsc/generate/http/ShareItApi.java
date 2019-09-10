package com.fsc.generate.http;

import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.pojo.AccountCard;
import com.fsc.generate.utils.AccountCardInfoDo;
import com.google.gson.*;
import com.valor.mfc.vms.api.model.common.response.ResponseMsgSingle;
import common.config.tools.config.ConfigTools3;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
public class ShareItApi {

    private static final Logger logger = LoggerFactory.getLogger(ShareItApi.class);
    private CCApiInterface ccApiInterface;
    private Gson gson;


    @PostConstruct
    private void init() {
        GsonBuilder builder = new GsonBuilder();

        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            @Override
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });

        gson = builder.create();

        OkHttpClient okHttpClient = initHttpClient();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigTools3.getString("crm.cc.server.url")).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        ccApiInterface = retrofit.create(CCApiInterface.class);


    }

    private static OkHttpClient initHttpClient() {
        return new OkHttpClient.Builder().readTimeout(60000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true)
                .connectTimeout(60000, TimeUnit.MILLISECONDS).build();
    }

    public Map<String, Object> createChargeCard(String did, int plan) {
        try {
            if (plan == 0) {
                plan = 1;
            } else if (plan == 1) {
                plan = 12;
            } else if (plan == 2) {
                plan = 24;
            }
            Response<Map<String, Object>> execute = ccApiInterface.createChargeCard("MFC", "0", "FREE", "BIZ_BASE", "0",
                    did, "1", String.valueOf(plan * 100)).execute();
            logger.info("Create charge card response : {}", execute.body());
            return execute.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createChargeCardV2(String did, int plan) throws Exception {
        try {
            if (plan == 0) {
                plan = 1;
            } else if (plan == 1) {
                plan = 12;
            } else if (plan == 2) {
                plan = 24;
            }
            Response<Map<String, Object>> response = ccApiInterface.createChargeCard(
                    "MFC", "0", "FREE", "BIZ_BASE", "0",
                    did, "1", String.valueOf(plan * 100)).execute();
            if(Objects.nonNull(response)
                    &&response.isSuccessful()
                    &&Objects.nonNull(response.body())){
                Map<String, Object> res = response.body();
                if(String.valueOf(res.get("retCode")).equalsIgnoreCase("0.0") &&
                        String.valueOf(res.get("errCode")).equalsIgnoreCase("0.0"))
                    return true;
            }
        } catch (Exception e) {
            logger.error("[createChargeCard] [ERROR] [{}] [{}]", did, plan, e);
        }
        throw CrmException.newCrmException(CrmCode.RET_API, CrmCode.ERR_API_CC_CREATE_CHARGE_CARD_FAILURE);
    }

    public ResponseMsgSingle<AccountCardInfoDo> createAccountCard() {
        try {
            Response<ResponseMsgSingle<AccountCardInfoDo>> execute = ccApiInterface.createAccountCard(
                    0,
                    "CRM-X-Test",
                    "CRM-X-Test",
                    1,
                    'Y',
                    0,
                    "CRM-X-Test",
                    1001
            ).execute();
            logger.info("Create account card response : {}", execute.body());
            return execute.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public AccountCard createAccountCardV2() throws CrmException {
        try {
            Response<ResponseMsgSingle<AccountCardInfoDo>> response = ccApiInterface.createAccountCard(
                    0,
                    "CRM-X-Test",
                    "CRM-X-Test",
                    1,
                    'Y',
                    0,
                    "CRM-X-Test",
                    1001
            ).execute();
            if(Objects.nonNull(response)
                    &&response.isSuccessful()
                    &&Objects.nonNull(response.body())){
                ResponseMsgSingle<AccountCardInfoDo> accountCardResponse= response.body();
                if(Objects.nonNull(accountCardResponse)
                        &&accountCardResponse.isSuccess()
                        &&Objects.nonNull(accountCardResponse.getResult())){
                    return new AccountCard(accountCardResponse.getResult().getAccountId(),
                            accountCardResponse.getResult().getPassword());
                }
            }
        } catch (Exception e) {
            logger.error("[createAccountCardV2] [ERROR]",  e);
        }
        throw CrmException.newCrmException(CrmCode.RET_API, CrmCode.ERR_API_CC_CREATE_ACCOUNT_CARD_FAILURE);
    }

}
