package com.fsc.generate.http;

import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.google.gson.*;
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
public class AdminUserApi {

    private static final Logger logger = LoggerFactory.getLogger(AdminUserApi.class);
    private AdminUserApiInterface adminUserApiInterface;
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigTools3.getString("crm.admin.user.url")).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        adminUserApiInterface = retrofit.create(AdminUserApiInterface.class);


    }

    private static OkHttpClient initHttpClient() {
        return new OkHttpClient.Builder().readTimeout(60000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true)
                .connectTimeout(60000, TimeUnit.MILLISECONDS).build();
    }

    public Map<String, Object> createUser(String did, int plan) {
        try {
            Response<Map<String, Object>> execute = adminUserApiInterface.createUser(
                    did,
                    "0",
                    plan == 0 ? 2000100 : 1000200,
                    "TEST",
                    plan,
                    0,
                    "TEST").execute();
            logger.info("Create user response : {}", execute.body());
            return execute.body();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean createUserV2(String did, int plan,long vendorId) throws Exception {
        try {
            Response<Map<String, Object>> response = adminUserApiInterface.createUser(
                    did,
                    "0",
                    vendorId,
                    "TEST",
                    plan,
                    0,
                    "TEST").execute();
            if(Objects.nonNull(response)
                    &&response.isSuccessful()
                    &&Objects.nonNull(response.body())){
                Map<String, Object> res = response.body();
                if(String.valueOf(res.get("retCode")).equalsIgnoreCase("0.0") &&
                        String.valueOf(res.get("errCode")).equalsIgnoreCase("0.0"))
                    return true;
            }
        } catch (Exception e) {
            logger.error("[createMFCUser] [ERROR] [{}] [{}]", did, plan, e);
        }
        throw CrmException.newCrmException(CrmCode.RET_API, CrmCode.ERR_API_VMS_USER_CREATE_USER_FAILURE);
    }

}
