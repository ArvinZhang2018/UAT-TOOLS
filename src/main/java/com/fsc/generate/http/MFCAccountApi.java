package com.fsc.generate.http;

import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.model.dto.biz.CreateEmailAccountReq;
import com.google.gson.*;
import com.valor.mfc.vms.api.common.encrypt.MD5;
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
public class MFCAccountApi {

    private static final Logger logger = LoggerFactory.getLogger(MFCAccountApi.class);
    private MFCAccountApiInterface mfcAccountApiInterface;
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigTools3.getString("crm.account.api.url")).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        mfcAccountApiInterface = retrofit.create(MFCAccountApiInterface.class);


    }

    private static OkHttpClient initHttpClient() {
        return new OkHttpClient.Builder().readTimeout(60000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true)
                .connectTimeout(60000, TimeUnit.MILLISECONDS).build();
    }

    public Map<String, Object> createUser(String email, String name, String password) {
        try {
            Response<Map<String, Object>> execute = mfcAccountApiInterface.
                    createUser(email, password, name, "120.77.225.194").execute();
            logger.info("Create user response : {}", execute.body());
            return execute.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createUserV2(CreateEmailAccountReq emailAccountReq) throws CrmException {
        try {
            Response<Map<String, Object>> response = mfcAccountApiInterface.
                    createUser(emailAccountReq.getEmail(),
                            MD5.MD5(emailAccountReq.getPassword()),
                            emailAccountReq.getNickname(),
                            "120.77.225.194").execute();
            if (Objects.nonNull(response)
                    && response.isSuccessful()
                    && Objects.nonNull(response.body())) {
                Map<String, Object> res = response.body();
                if (String.valueOf(res.get("retCode")).equalsIgnoreCase("0.0") &&
                        String.valueOf(res.get("errCode")).equalsIgnoreCase("0.0")) {
                    return true;
                }
            }
        } catch (Exception e) {
            logger.error("[createUserV2] [ERROR] [{}]", emailAccountReq, e);
        }
        throw CrmException.newCrmException(CrmCode.RET_API, CrmCode.ERR_API_ACCOUNT_CREATE_EMAIL_ACCOUNT_FAILURE);
    }

}
