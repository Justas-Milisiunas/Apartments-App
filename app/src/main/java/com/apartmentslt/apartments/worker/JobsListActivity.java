package com.apartmentslt.apartments.worker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.GenericAdapter;
import com.apartmentslt.apartments.models.Work;
import com.apartmentslt.apartments.profile.activities.ProfileActivity;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.services.JobsService;
import com.bumptech.glide.Glide;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.chip.Chip;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class JobsListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    GenericAdapter<Work> mAdapter;
    List<Work> allJobs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);

        allJobs = new ArrayList<>();
        mAdapter = initializeRecyclerView();
        loadData();

        // Add top toolbar
        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();

        // Add bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_toolbar);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottomNavigationView.getMenu().findItem(R.id.navigation_jobs_list).setEnabled(false); // Disable apartments list button
        } else {
            Toast.makeText(this, "Bottom navigation bar could not be loaded", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Initializes recycler view for displaying jobs data
     *
     * @return Created recycler view adapter
     */
    private GenericAdapter<Work> initializeRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.jobs_list);
        GenericAdapter<Work> jobsAdapter = new GenericAdapter<Work>(this) {
            /**
             * Each item layout id
             * @return Layout id
             */
            @Override
            public int getLayoutId() {
                return R.layout.jobs_list_item;
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
                TextView name = ((TextView) viewHolder.getComponent(R.id.jobName));
                name.setText(model.getButas().getPavadinimas());

                TextView address = ((TextView) viewHolder.getComponent(R.id.jobAddress));
                address.setText(model.getButas().getAdresas());

                TextView salary = ((TextView) viewHolder.getComponent(R.id.jobSalary));
                salary.setText(String.valueOf(model.getUzmokestis()));
            }
            /**
             * Starts activity for showing jobs data
             * TODO: Pass photo through intent
             * @param item Clicked item data
             * @param position Clicked item position in list
             */
            @Override
            public void onClick(Work item, int position) {
                Intent intent = new Intent(getApplicationContext(), JobDetailsActivity.class);
                try {
                    intent.putExtra(JobDetailsActivity.JOB_DATA_KEY, item);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    getBaseContext().startActivity(intent);
                } catch (Exception e) {
                    Log.d("[ERROR]", e.getMessage());
                }
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
        final Call<List<Work>> requestCall = jobsService.getAllJobs();
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

    /**
     * Inflates toolbar menu items for the toolbar
     *
     * @param menu Menu
     * @return true if inflated successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.profile_menu, menu);
        return true;
    }

    /**
     * Menu items click listener
     *
     * @param item Selected menu item
     * @return true if commands initiated successfully
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }
        return true;
    }

    /**
     * Bottom navigation bar clicked menu items listener
     *
     * @param menuItem Clicked menu item
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.navigation_jobs_list:
                return true;
            case R.id.navigation_history_list:
                Intent jobsHistoryIntent = new Intent(this, JobsHistoryActivity.class);
                startActivity(jobsHistoryIntent);
                return true;
            case R.id.navigation_jobs_report:
                Intent reportIntent = new Intent(this, ReportActivity.class);
                startActivity(reportIntent);
                return true;
        }
        return false;
    }
}
