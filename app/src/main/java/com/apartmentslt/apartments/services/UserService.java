package com.apartmentslt.apartments.services;

import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.models.UserLoginDto;
import com.apartmentslt.apartments.models.UserRegistrationDto;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface UserService {
    @POST("api/users/register")
    Call<User> register(@Body UserRegistrationDto user);

    @POST("api/users/login")
    Call<User> login(@Body UserLoginDto user);

    @DELETE("api/users/{id}")
    Call<ResponseBody> deleteProfile(@Path("id") int id);
}
