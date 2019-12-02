package com.apartmentslt.apartments.services;

import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.models.UserLoginDto;
import com.apartmentslt.apartments.models.UserRegistrationDto;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {
    @POST("api/users/register")
    Call<User> register(@Body UserRegistrationDto user);

    @POST("api/users/login")
    Call<User> login(@Body UserLoginDto user);
}
