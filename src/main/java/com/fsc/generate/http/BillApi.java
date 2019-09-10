package com.fsc.generate.http;

import com.fsc.generate.utils.ConfigService;
import com.fsc.generate.utils.EApiType;
import com.google.gson.*;
import com.valor.mfc.vms.api.model.common.response.ResponseStatus;
import common.config.tools.config.ConfigTools3;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Callback;
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
public class BillApi {

    private static final Logger logger = LoggerFactory.getLogger(BillApi.class);
    private BillApiInterface billApiInterface;
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
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigTools3.getString("crm.bill.api.url")).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        billApiInterface = retrofit.create(BillApiInterface.class);
    }

    private static OkHttpClient initHttpClient() {
        return new OkHttpClient.Builder().readTimeout(60000, TimeUnit.MILLISECONDS).retryOnConnectionFailure(true)
                .connectTimeout(60000, TimeUnit.MILLISECONDS).build();
    }

    public ResponseBody sendNotification(String notification) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), notification);
        Response<ResponseBody> response = billApiInterface.sendNotification(body).execute();
        if (Objects.nonNull(response)
                && response.isSuccessful()
                && Objects.nonNull(response.body())) {
            return response.body();
        }
        return null;
    }

}
