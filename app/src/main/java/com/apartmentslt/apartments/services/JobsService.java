package com.apartmentslt.apartments.services;

import com.apartmentslt.apartments.models.JobUpdateDto;
import com.apartmentslt.apartments.models.ReportGenerateDto;
import com.apartmentslt.apartments.models.Work;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface JobsService {

    @GET("api/jobs")
    Call<List<Work>> getAllJobs();

    @GET("api/job/{id}")
    Call<Work> getJobByID(@Path("id") int id);

    @GET("api/jobs/history/{id}")
    Call<List<Work>> getJobsHistory(@Path("id") int id);

    @PUT("api/jobs/accept")
    Call<Work> makeJobAsTaken(@Body JobUpdateDto jobUpdate);

    @PUT("api/jobs/done")
    Call<Work> makeJobAsDone(@Body JobUpdateDto jobUpdate);

    @PUT("api/jobs/delete")
    Call<Work> cancelJob(@Body JobUpdateDto jobUpdate);
}
