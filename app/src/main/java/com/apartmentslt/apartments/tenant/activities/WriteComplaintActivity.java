package com.apartmentslt.apartments.tenant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.Complaint;
import com.apartmentslt.apartments.models.SearchOptions;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.profile.activities.ProfileActivity;
import com.apartmentslt.apartments.services.ApartmentsService;
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

public class WriteComplaintActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    List<Apartment> allApartments;
    EditText complaintText;
    Spinner apartmentsSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_complaint);

        // Adds toolbar
        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();

        findComponents();
        loadRentedApartments();

        // Adds bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_toolbar);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottomNavigationView.getMenu().findItem(R.id.navigation_write_complaint).setEnabled(false); // Disable write complaint button
        } else {
            Toast.makeText(this, "Bottom navigation bar could not be loaded", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Finds  components from layout
     */
    private void findComponents() {
        complaintText = findViewById(R.id.complaint_text);
        apartmentsSpinner = findViewById(R.id.apartments_spinner);
    }

    /**
     * Loads user's rented apartments data
     */
    private void loadRentedApartments() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        SearchOptions searchOptions = new SearchOptions();
        searchOptions.setTenantId(User.getInstance().getIdIsNaudotojas());

        allApartments = new ArrayList<>();

        ApartmentsService apartmentsService = retrofit.create(ApartmentsService.class);
        final Call<List<Apartment>> requestCall = apartmentsService.searchApartments(searchOptions);
        requestCall.enqueue(new Callback<List<Apartment>>() {
            /**
             * Loads all apartments which local user rents
             * @param call Call
             * @param response Request response
             */
            @Override
            public void onResponse(Call<List<Apartment>> call, Response<List<Apartment>> response) {
                if (response.isSuccessful()) {
                    List<Apartment> apartments = response.body();
                    if (apartments == null) {
                        Toast.makeText(getApplicationContext(), "Could not load any apartments", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    allApartments.addAll(apartments);
                    loadSpinnerData();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            /**
             * Could not load any apartments
             * @param call Call
             * @param t Error
             */
            @Override
            public void onFailure(Call<List<Apartment>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Sends complaint to apartments API
     */
    public void sendComplaint(View view) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Makes complaint object
        Complaint complaint = new Complaint(complaintText.getText().toString(),
                allApartments.get(apartmentsSpinner.getSelectedItemPosition()).getIdButas(),
                User.getInstance().getIdIsNaudotojas());

        ApartmentsService apartmentsService = retrofit.create(ApartmentsService.class);
        final Call<Complaint> request = apartmentsService.makeComplaint(complaint);
        request.enqueue(new Callback<Complaint>() {
            /**
             * If request was successfully shows message and finishes this activity to go back to apartment details
             * @param call Call
             * @param response Request response
             */
            @Override
            public void onResponse(Call<Complaint> call, Response<Complaint> response) {
                if (response.isSuccessful()) {
                    Complaint savedComplaint = response.body();
                    if (savedComplaint == null) {
                        Toast.makeText(getApplicationContext(), "Could not load saved complaint", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(getApplicationContext(), "Complaint was successfully sent", Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            /**
             * If request was not successful shows error message
             * @param call Call
             * @param t Error
             */
            @Override
            public void onFailure(Call<Complaint> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Loads apartments data to spinner
     */
    private void loadSpinnerData() {
        Spinner apartmentsSpinner = findViewById(R.id.apartments_spinner);

        String[] apartmentsNames = new String[allApartments.size()];
        for (int i = 0; i < apartmentsNames.length; i++) {
            apartmentsNames[i] = allApartments.get(i).getPavadinimas();
        }

        ArrayAdapter<String> apartmentsAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, apartmentsNames);
        apartmentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        apartmentsSpinner.setAdapter(apartmentsAdapter);
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
            case R.id.navigation_apartments_list:
                finish(); // Closes active activity
                break;
            case R.id.navigation_write_complaint:
                break;
        }
        return false;
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
     * Shows filter dialog after pressing filter icon
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
}
