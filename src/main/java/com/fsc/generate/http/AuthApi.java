package com.fsc.generate.http;

import com.fsc.generate.utils.ConfigService;
import com.fsc.generate.utils.EApiType;
import com.google.gson.*;
import common.config.tools.config.ConfigTools3;
import okhttp3.OkHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
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
public class AuthApi {

    private static final Logger logger = LoggerFactory.getLogger(AuthApi.class);
    private AuthApiInterface authApiInterface;
    private Gson gson;
    @Autowired
    private ConfigService configService;

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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigTools3.getString("crm.auth.api.url")).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        authApiInterface = retrofit.create(AuthApiInterface.class);

    }

    private static OkHttpClient initHttpClient() {
        return new OkHttpClient.Builder().readTimeout(60000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true)
                .connectTimeout(60000, TimeUnit.MILLISECONDS).build();
    }

    public Map<String, Object> invokeApi(Map<String, String> params, String api) {
        Response<Map<String, Object>> response = null;
        try {
            switch (EApiType.valueOf(api.toUpperCase())) {
                case ACCOUNT_LOGIN:
                    response = authApiInterface.accountLogin(params).execute();
                    break;
                case DEVICE_LOGIN:
                    break;
            }
        } catch (IOException e) {
            logger.error("Call api[{}] failure ", api, e);
        }
        return Objects.nonNull(response) ? response.body() : null;
    }

}
