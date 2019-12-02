package com.apartmentslt.apartments.services;

import com.apartmentslt.apartments.models.Apartment;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApartmentsService {
    @GET("api/apartments")
    Call<List<Apartment>> getAll();
}
