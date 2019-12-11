package com.apartmentslt.apartments.worker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobService;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.JobUpdateDto;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.models.Work;
import com.apartmentslt.apartments.services.JobsService;
import com.apartmentslt.apartments.services.UserService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobDetailsActivity extends AppCompatActivity {
    private static final String TAG = JobDetailsActivity.class.getName();
    public final static String JOB_DATA_KEY = "com.apartmentslt.apartments.JOB_DATA";

    private Work currentWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        this.currentWork = getCurrentJob();
        if (this.currentWork != null){
            bindData(this.currentWork);
        }

        setCancelJobButtonVisibility();
        setTakeJobButtonVisibility();
        setDoneWorkButtonVisibility();

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
    }

    /**
     * Shows or hides booking cancel button based on if user rented this apartment
     */
    private void setTakeJobButtonVisibility() {
        Button take = findViewById(R.id.takeJobButton);
        int jobStatus = getJobStatus();

        if (jobStatus != 3) {
            take.setVisibility(View.GONE);
        } else {
            take.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Shows or hides booking cancel button based on if user rented this apartment
     */
    private void setCancelJobButtonVisibility() {
        Button take = findViewById(R.id.jobCancelButton);
        int jobStatus = getJobStatus();
        boolean isUserIn = checkIfUserIsSign();

        if (isUserIn && jobStatus == 2) {
            take.setVisibility(View.VISIBLE);
        } else {
            take.setVisibility(View.GONE);
        }
    }

    /**
     * Shows or hides booking cancel button based on if user rented this apartment
     */
    private void setDoneWorkButtonVisibility() {
        Button take = findViewById(R.id.jobDoneButton);
        int jobStatus = getJobStatus();
        boolean isUserIn = checkIfUserIsSign();

        if (jobStatus == 2 && isUserIn) {
            take.setVisibility(View.VISIBLE);
        } else {
            take.setVisibility(View.GONE);
        }
    }

    private boolean checkIfUserIsSign(){
        return User.getInstance().getIdIsNaudotojas() == currentWork.getFkValytojasidIsNaudotojas();
    }

    private int getJobStatus(){
        return currentWork.getBusena();
    }

    /**
     * Binds job data to components
     *
     * @param job Job
     */
    private void bindData(Work job) {
        String[] splitedDate = String.valueOf(job.getSukurimoData()).split(" ");
        String date = splitedDate[0] + " " + splitedDate[1] + " " + splitedDate[2] + " " + splitedDate[5];

        ((TextView) findViewById(R.id.jobName)).setText(job.getButas().getPavadinimas());
        ((TextView) findViewById(R.id.jobCreationDate)).setText("Created: " + date);
        ((TextView) findViewById(R.id.jobAddress)).setText(job.getButas().getMiestas() + " " + job.getButas().getAdresas());
        ((TextView) findViewById(R.id.jobPrice)).setText(String.valueOf(job.getUzmokestis()));
        ((TextView) findViewById(R.id.apartmentSize)).setText(String.valueOf(job.getButas().getDydis()));
    }

    /**
     * Extracts jobs data from intent
     *
     * @return job data
     */
    private Work getCurrentJob() {
        Intent intent = getIntent();
        Work job = null;
        if (intent.getExtras() != null) {
            job = ((Work) intent.getExtras().getSerializable(JOB_DATA_KEY));
            if (job == null) {
                Toast.makeText(this, "Could not load apartment", Toast.LENGTH_LONG).show();
            }
        }
        return job;
    }

    public void setJobTaken(View view) {
        JobUpdateDto updateJob = new JobUpdateDto(User.getInstance().getIdIsNaudotojas(),
                currentWork.getIdDarbas());
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JobsService jobService = retrofit.create(JobsService.class);
        final Call<Work>requestCall = jobService.makeJobAsTaken(updateJob);
        requestCall.enqueue(new Callback<Work>() {
            /**
             * If request successful logouts user
             * @param call Call
             * @param response Request response
             */
            @Override
            public void onResponse(Call<Work> call, Response<Work> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Job is Yours!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }
            /**
             * If error shows toast message
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Work> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setJobDone(View view) {
        JobUpdateDto updateJob = new JobUpdateDto(User.getInstance().getIdIsNaudotojas(),
                currentWork.getIdDarbas());
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JobsService jobService = retrofit.create(JobsService.class);
        final Call<Work>requestCall = jobService.makeJobAsDone(updateJob);
        requestCall.enqueue(new Callback<Work>() {
            /**
             * If request successful logouts user
             * @param call Call
             * @param response Request response
             */
            @Override
            public void onResponse(Call<Work> call, Response<Work> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Job is Done!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }
            /**
             * If error shows toast message
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Work> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setJobCancel(View view) {
        JobUpdateDto updateJob = new JobUpdateDto(User.getInstance().getIdIsNaudotojas(),
                currentWork.getIdDarbas());
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JobsService jobService = retrofit.create(JobsService.class);
        final Call<Work>requestCall = jobService.cancelJob(updateJob);
        requestCall.enqueue(new Callback<Work>() {
            /**
             * If request successful logouts user
             * @param call Call
             * @param response Request response
             */
            @Override
            public void onResponse(Call<Work> call, Response<Work> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Job Canceled!", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }
            /**
             * If error shows toast message
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<Work> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
