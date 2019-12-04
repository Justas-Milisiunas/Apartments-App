package com.apartmentslt.apartments.services;

import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.RentPeriod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;

public interface ApartmentsService {
    @GET("api/apartments")
    Call<List<Apartment>> getAll();

    @PUT("api/apartments/book")
    Call<RentPeriod> makeReservation(@Body RentPeriod reservation);
}
