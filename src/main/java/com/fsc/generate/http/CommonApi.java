package com.fsc.generate.http;

import com.fsc.generate.exception.CrmCode;
import com.fsc.generate.exception.CrmException;
import com.fsc.generate.utils.ReflectTools;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.valor.mfc.vms.api.model.common.response.ResponseStatus;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.POST;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public abstract class CommonApi<T> {

    private static final Logger logger = LoggerFactory.getLogger(CommonApi.class);

    private Gson gson = new Gson();

    private T apiInterface;
    private String url;
    private long readTimeOut;
    private long connectTimeOut;
    private Class<T> apiInterfaceType;

    protected abstract void initParams() throws Exception;

    private boolean interfaceIsNotNull() {
        return Objects.nonNull(apiInterface);
    }

    protected void init() throws Exception {
        GsonBuilder builder = new GsonBuilder();
        builder.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
            public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
                    throws JsonParseException {
                return new Date(json.getAsJsonPrimitive().getAsLong());
            }
        });
        Gson gson = builder.create();
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder()
                .readTimeout(getReadTimeOut(), TimeUnit.MILLISECONDS)
                .retryOnConnectionFailure(true)
                .connectTimeout(getConnectTimeOut(), TimeUnit.MILLISECONDS);
        OkHttpClient okHttpClient = clientBuilder.build();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(getUrl()).client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson)).build();
        setApiInterface(retrofit.create(getApiInterfaceType()));
    }

    protected T getApiInterface() throws Exception {
        try {
            if (!interfaceIsNotNull()) {
                init();
            }
        } catch (Exception e) {
            logger.error("Initialize interface error!", e);
        }
        if (Objects.isNull(apiInterface)) {
            throw CrmException.newCrmException(CrmCode.RET_API, CrmCode.ERR_API_CALL_FAILURE,
                    "Initialize interface failure!");
        }
        return apiInterface;
    }

    private String getCallUrl(Method typeMethod) {
        if (Objects.nonNull(typeMethod.getAnnotation(POST.class))) {
            return url + typeMethod.getAnnotation(POST.class).value();
        } else if (Objects.nonNull(typeMethod.getAnnotation(GET.class))) {
            return url + typeMethod.getAnnotation(GET.class).value();
        }
        return url;
    }

    public <V extends ResponseStatus> V innerInvoke(String method, Object... req) throws CrmException {
        try {
            Method typeMethod = ReflectTools.getMethod(method, getApiInterfaceType());
            Call<V> invoke = (Call<V>) typeMethod.invoke(getApiInterface(), req);
            Response<V> response = invoke.execute();
            if (response.isSuccessful()) {
                V body = response.body();
                if (body.isSuccess()) {
                    return body;
                } else {
                    throw CrmException.newCrmException(body.getRetCode(), body.getErrCode(), body.getMessage());
                }
            } else {
                ResponseBody errorBody = response.errorBody();
                if (Objects.isNull(errorBody)) {
                    throw CrmException.newCrmException(CrmCode.RET_API, CrmCode.ERR_API_CALL_FAILURE, "Empty errorBody.");
                }
                String content = errorBody.string();
                ResponseStatus responseMsg = gson.fromJson(content,
                        new TypeToken<ResponseStatus>() {
                        }.getType());
                if (!responseMsg.isSuccess()) {
                    throw CrmException.newCrmException(responseMsg.getRetCode(), responseMsg.getErrCode(), responseMsg.getMessage());
                } else {
                    throw CrmException.newCrmException(CrmCode.RET_API, CrmCode.ERR_API_CALL_FAILURE,
                            "Wrong httpCode-" + response.code());
                }
            }
        } catch (Exception e) {
            if (e instanceof CrmException) {
                throw (CrmException) e;
            }
            throw CrmException.newCrmException(CrmCode.RET_API, CrmCode.ERR_API_CALL_FAILURE, e.getMessage());
        }
    }


    public void setApiInterface(T apiInterface) {
        this.apiInterface = apiInterface;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public long getReadTimeOut() {
        return readTimeOut;
    }

    public void setReadTimeOut(long readTimeOut) {
        this.readTimeOut = readTimeOut;
    }

    public long getConnectTimeOut() {
        return connectTimeOut;
    }

    public void setConnectTimeOut(long connectTimeOut) {
        this.connectTimeOut = connectTimeOut;
    }

    public Class<T> getApiInterfaceType() {
        return apiInterfaceType;
    }

    public void setApiInterfaceType(Class<T> apiInterfaceType) {
        this.apiInterfaceType = apiInterfaceType;
    }

}
