package com.apartmentslt.apartments.worker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.GenericAdapter;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.models.Work;
import com.apartmentslt.apartments.services.JobsService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobsHistoryActivity extends AppCompatActivity{
    GenericAdapter<Work> mAdapter;
    List<Work> allJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_history);

        allJobs = new ArrayList<>();
        mAdapter = initializeRecyclerView();
        loadData();

        // Add top toolbar
        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
    }

    /**
     * Initializes recycler view for displaying jobs data
     *
     * @return Created recycler view adapter
     */
    private GenericAdapter<Work> initializeRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.jobsHistory_list);
        GenericAdapter<Work> jobsAdapter = new GenericAdapter<Work>(this) {
            /**
             * Each item layout id
             * @return Layout id
             */
            @Override
            public int getLayoutId() {
                return R.layout.jobs_history_item;
            }

            /**
             * Binds current item data to layout components
             * @param model Current item Item
             * @param position Current item's position Position in list
             * @param viewHolder Inflated layout view Layout view holder
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void onBindData(Work model, int position, ItemViewHolder viewHolder) {
                String[] splitedDate = String.valueOf(model.getIvykdymoData()).split(" ");
                String datee = splitedDate[0] + " " + splitedDate[1] + " " + splitedDate[2] + " " + splitedDate[5];

                TextView name = ((TextView) viewHolder.getComponent(R.id.jobHistoryName));
                name.setText(model.getButas().getPavadinimas());

                TextView address = ((TextView) viewHolder.getComponent(R.id.jobHistoryAddress));
                address.setText(model.getButas().getAdresas());

                TextView salary = ((TextView) viewHolder.getComponent(R.id.jobHistorySalary));
                salary.setText(String.valueOf(model.getUzmokestis()));

                TextView date = ((TextView) viewHolder.getComponent(R.id.jobDoneDate));
                date.setText("Done at: "+ datee);
            }
            /**
             * Starts activity for showing jobs data
             * TODO: Pass photo through intent
             * @param item Clicked item data
             * @param position Clicked item position in list
             */
            @Override
            public void onClick(Work item, int position) {

            }
        };
        mRecyclerView.setAdapter(jobsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        return jobsAdapter;
    }

    /**
     * Loads and adds data to the list
     */
    private void loadData() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        JobsService jobsService = retrofit.create(JobsService.class);
        final Call<List<Work>> requestCall = jobsService.getJobsHistory(User.getInstance().getIdIsNaudotojas());
        requestCall.enqueue(new Callback<List<Work>>() {
            /**
             * If request to jobs API was successful loads jobs data
             * @param call Call
             * @param response Response
             */
            @Override
            public void onResponse(Call<List<Work>> call, Response<List<Work>> response) {
                if (response.isSuccessful()) {
                    List<Work> jobs = response.body();
                    if (jobs == null) {
                        Toast.makeText(getApplicationContext(), "Could not load any jobs", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    allJobs.addAll(jobs);

                    showJobs(allJobs);
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            /**
             * If request to jobs API was unsuccessful shows error message
             * @param call Call
             * @param t exception
             */
            @Override
            public void onFailure(Call<List<Work>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Adds given jobs to the recycler view  list
     *
     * @param jobs Jobs list
     */
    private void showJobs(List<Work> jobs) {
        mAdapter.clear();
        if (jobs.size() == 0)
            Toast.makeText(this, "No jobs found", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, jobs.size() + " jobs found", Toast.LENGTH_SHORT).show();

        for (Work job : jobs) {
                mAdapter.addItem(job);
        }
    }
}
