package com.pitt.asesoriasitt.api;

import com.pitt.asesoriasitt.db.models.Advisory;
import com.pitt.asesoriasitt.db.models.User;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AsesoriasService {

    @GET(APIConstants.wsPath + "get_all_users/{userType}/")
    Call<ArrayList<User>> getAllUsers(@Path("userType") long userType);

    @GET(APIConstants.wsPath + "user/{userId}/")
    Call<User> getUserDetail(@Path("userId") long userId);

    @POST(APIConstants.wsPath + "user/{userId}/create_advisory")
    Call<GenericResponse> createAdvisory(@Path("userId") long userId, @Body Advisory advisory);

    @POST(APIConstants.wsPath + "user/{userId}/update")
    Call<GenericResponse> updateUser(@Path("userId") long userId, @Body User user);

    @POST(APIConstants.wsPath + "login")
    Call<GenericResponse<User>> login(@Body User user);
}
