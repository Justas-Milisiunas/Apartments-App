package com.apartmentslt.apartments.services;

import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.ApartmentDeleteDto;
import com.apartmentslt.apartments.models.CancelBooking;
import com.apartmentslt.apartments.models.Complaint;
import com.apartmentslt.apartments.models.Rating;
import com.apartmentslt.apartments.models.RentPeriod;
import com.apartmentslt.apartments.models.ReportGenerateDto;
import com.apartmentslt.apartments.models.SearchOptions;
import com.apartmentslt.apartments.owner.activities.ApartmentDetailsWithEditButtonActivity;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApartmentsService {
    @GET("api/apartments")
    Call<List<Apartment>> getAll();

    @POST("api/apartments/search")
    Call<List<Apartment>> searchApartments(@Body SearchOptions searchOptions);
    @POST("api/apartments/searchComplaints")
    Call<List<Complaint>> searchComplaints(@Body SearchOptions searchOptions);

    @Multipart
    @POST("api/apartments/addApartment")
    Call<RequestBody> addApartment(
            @Part("apartment") Apartment apartment,
            @Part MultipartBody.Part nuotrauka
    );


    @POST("api/apartments/report")
    Call<RequestBody> generateReport(@Body ReportGenerateDto reportGenerateDto);
    @POST("api/apartments/updateApartment")
    Call<Apartment> updateApartment(@Body SearchOptions searchOptions);

    @POST("api/apartments/deleteApartment")
    Call<RequestBody> deleteApartment(@Body ApartmentDeleteDto apartmentDeleteDto);

    @PUT("api/apartments/book")
    Call<RentPeriod> makeReservation(@Body RentPeriod reservation);

    @POST("api/apartments/complaint")
    Call<Complaint> makeComplaint(@Body Complaint complaint);

    @POST("api/apartments/book/cancel")
    Call<ResponseBody> cancelBooking(@Body CancelBooking cancelDate);

    @POST("api/apartments/rate")
    Call<Rating> rateApartment(@Body Rating rating);
}
