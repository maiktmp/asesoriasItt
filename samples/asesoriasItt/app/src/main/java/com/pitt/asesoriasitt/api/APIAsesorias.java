package com.pitt.asesoriasitt.api;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.pitt.asesoriasitt.api.serializers.BooleanDeserializer;
import com.pitt.asesoriasitt.api.serializers.BooleanSerializer;
import com.pitt.asesoriasitt.api.serializers.DateDeserializaer;
import com.pitt.asesoriasitt.api.serializers.DateSerializer;
import com.pitt.asesoriasitt.db.models.Advisory;
import com.pitt.asesoriasitt.db.models.User;
import com.pitt.asesoriasitt.utils.callbacks.CBSuccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class APIAsesorias {
    private static final String TAG = "API_ASESORIAS";
    private static APIAsesorias instance;
    private AsesoriasService apiService;


    public static APIAsesorias getInstance() {
        if (instance == null) {
            instance = new APIAsesorias();
        }
        return instance;
    }

    private APIAsesorias() {
// Request log interceptor
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        // Prepare http clientfi
        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor);

        // Prepare Gson instance
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Boolean.class, new BooleanSerializer())
                .registerTypeAdapter(Boolean.class, new BooleanDeserializer())
                .registerTypeAdapter(boolean.class, new BooleanSerializer())
                .registerTypeAdapter(boolean.class, new BooleanDeserializer())
                .registerTypeAdapter(Date.class, new DateSerializer())
                .registerTypeAdapter(Date.class, new DateDeserializaer())
                .create();

        // Prepare retrofit instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.serverPath)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(httpClientBuilder.build())
                .build();

        apiService = retrofit.create(AsesoriasService.class);
    }

    public void getAllUserType(long userTypeId, CBSuccess<ArrayList<User>> cb) {
        doRequest(
                "Get users",
                apiService.getAllUsers(userTypeId),
                cb
        );
    }

    public void getUserDetail(long userId, CBSuccess<User> cb) {
        doRequest(
                "Get user detail",
                apiService.getUserDetail(userId),
                cb
        );
    }

    public void createAdvisory(long userId, Advisory advisory, CBSuccess<GenericResponse> cb) {
        doRequest(
                "Get user detail",
                apiService.createAdvisory(userId, advisory),
                cb
        );
    }

    public void updateUser(User user, CBSuccess<GenericResponse> cb) {
        doRequest(
                "Get user detail",
                apiService.updateUser(user.getId(), user),
                cb
        );
    }


    @SuppressWarnings("unchecked")
    private void doRequest(final String operation, Call call, final CBSuccess cb) {
        call.enqueue(new Callback() {
            @SuppressWarnings("NullableProblems")
            @Override
            public void onResponse(Call call, Response response) {
                if (response.isSuccessful()) {
                    cb.onResponse(true, response.body());
                } else {
                    handleUnsuccessful(operation, cb);
                }
            }

            @SuppressWarnings("NullableProblems")
            @Override
            public void onFailure(Call call, Throwable t) {
                handleFailure(operation, t, cb);
            }
        });
    }

    @SuppressWarnings("unchecked")
    private void handleUnsuccessful(String operation, CBSuccess callback) {
        Log.w(TAG, operation + " was unsuccessful");
        callback.onResponse(false, null);
    }

    @SuppressWarnings("unchecked")
    private void handleFailure(String operation, Throwable t,
                               CBSuccess callback) {
        Log.e(TAG, operation + " has failed");
        Log.e(TAG, "Message is: " + t.getMessage());

        callback.onResponse(false, null);
    }

}
