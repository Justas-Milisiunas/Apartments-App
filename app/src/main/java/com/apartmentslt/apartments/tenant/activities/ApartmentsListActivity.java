package com.apartmentslt.apartments.tenant.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.GenericAdapter;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.profile.activities.ProfileActivity;
import com.apartmentslt.apartments.services.ApartmentsService;
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

public class ApartmentsListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    GenericAdapter<Apartment> mAdapter;
    List<Apartment> allApartments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);

        allApartments = new ArrayList<>();
        mAdapter = initializeRecyclerView();
        loadData();

        // Add top toolbar
        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();

        // Add bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_toolbar);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottomNavigationView.getMenu().findItem(R.id.navigation_apartments_list).setEnabled(false); // Disable apartments list button
        } else {
            Toast.makeText(this, "Bottom navigation bar could not be loaded", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Initializes recycler view for displaying apartments data
     *
     * @return Created recycler view adapter
     */
    private GenericAdapter<Apartment> initializeRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.apartments_list);
        GenericAdapter<Apartment> apartmentsAdapter = new GenericAdapter<Apartment>(this) {
            /**
             * Each item layout id
             * @return Layout id
             */
            @Override
            public int getLayoutId() {
                return R.layout.apartments_list_item;
            }

            /**
             * Binds current item data to layout components
             * @param model Current item Item
             * @param position Current item's position Position in list
             * @param viewHolder Inflated layout view Layout view holder
             */
            @SuppressLint("SetTextI18n")
            @Override
            public void onBindData(Apartment model, int position, ItemViewHolder viewHolder) {
                TextView name = ((TextView) viewHolder.getComponent(R.id.name));
                name.setText(model.getPavadinimas());

                TextView address = ((TextView) viewHolder.getComponent(R.id.address));
                address.setText(model.getAdresas());

                TextView price = ((TextView) viewHolder.getComponent(R.id.price));
                price.setText(model.getKainaUzNakti() + " per night");

                TextView size = ((TextView) viewHolder.getComponent(R.id.size));
                size.setText(String.valueOf(model.getDydis()) + " m²");

                Chip rooms = ((Chip) viewHolder.getComponent(R.id.rooms));
                rooms.setText(model.getKambaruSkaicius() + " rooms");

                RatingBar ratingBar = ((RatingBar) viewHolder.getComponent(R.id.rating_bar));
                ratingBar.setRating(model.calculateRating());


                ImageView image = ((ImageView) viewHolder.getComponent(R.id.apartment_image));
                Glide.with(getApplicationContext())
                        .load(model.getNuotraukaUrl())
                        .error(R.drawable.ic_error)
                        .into(image);
            }

            /**
             * Starts activity for showing apartments data
             * TODO: Pass photo through intent
             * @param item Clicked item data
             * @param position Clicked item position in list
             */
            @Override
            public void onClick(Apartment item, int position) {
                Intent intent = new Intent(getApplicationContext(), ApartmentDetailsActivity.class);
                try {
                    intent.putExtra(ApartmentDetailsActivity.APARTMENT_DATA_KEY, item);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                    getBaseContext().startActivity(intent);
                } catch (Exception e) {
                    Log.d("[ERROR]", e.getMessage());
                }
            }
        };

        mRecyclerView.setAdapter(apartmentsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        return apartmentsAdapter;
    }

    /**
     * Loads and adds data to the list
     */
    private void loadData() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        ApartmentsService apartmentsService = retrofit.create(ApartmentsService.class);
        final Call<List<Apartment>> requestCall = apartmentsService.getAll();
        requestCall.enqueue(new Callback<List<Apartment>>() {
            /**
             * If request to apartments API was successful loads apartments data
             * @param call Call
             * @param response Response
             */
            @Override
            public void onResponse(Call<List<Apartment>> call, Response<List<Apartment>> response) {
                if (response.isSuccessful()) {
                    List<Apartment> apartments = response.body();
                    if (apartments == null) {
                        Toast.makeText(getApplicationContext(), "Could not load any apartments", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    for (Apartment apartment : apartments) {
                        allApartments.add(apartment);
                    }

                    showApartments(allApartments);
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            /**
             * If request to apartments API was unsuccessful shows error message
             * @param call Call
             * @param t exception
             */
            @Override
            public void onFailure(Call<List<Apartment>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                return true;
            case R.id.navigation_write_complaint:
                Intent complaintIntent = new Intent(this, WriteComplaintActivity.class);
                startActivity(complaintIntent);
                return true;
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
        menuInflater.inflate(R.menu.apartments_list_menu, menu);
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
        if (item.getItemId() == R.id.action_filter) {
            FilterDialog filterDialog = new FilterDialog();
            filterDialog.show(getSupportFragmentManager(), "FilterDialogFragment");
        }
        if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

        return true;
    }

    /**
     * Extracts all cities from apartments array
     *
     * @return Cities list
     */
    public List<String> getAllCities() {
        List<String> cities = new ArrayList<>();
        cities.add("");
        for (Apartment apartment : allApartments) {
            if (!cities.contains(apartment.getMiestas())) {
                cities.add(apartment.getMiestas());
            }
        }

        return cities;
    }

    /**
     * Extracts available rooms numbers from apartments data
     *
     * @return Room numbers list
     */
    public List<String> getNumberOfRooms() {
        List<String> roomNumbers = new ArrayList<>();
        roomNumbers.add("");
        for (Apartment apartment : allApartments) {
            if (!roomNumbers.contains(String.valueOf(apartment.getKambaruSkaicius()))) {
                roomNumbers.add(String.valueOf(apartment.getKambaruSkaicius()));
            }
        }

        return roomNumbers;
    }

    /**
     * Filters apartments by given dialog's filter options
     * TODO: remove hardcoded price range end
     *
     * @param city      City name
     * @param rooms     Number  of rooms
     * @param priceFrom Price range start
     * @param priceTo   Price range end
     */
    public void filterApartments(String city, String rooms, String priceFrom, String priceTo) {
        List<Apartment> filteredApartments = new ArrayList<>();

        Double priceStart = 0.0;
        Double priceEnd = 10000.0;

        if (!priceFrom.equals(""))
            priceStart = Double.parseDouble(priceFrom);
        if (!priceTo.equals(""))
            priceEnd = Double.parseDouble(priceTo);


        for (Apartment apartment : allApartments) {
            boolean addApartment = false;
            if (city.equals("") || apartment.getMiestas().equals(city)) {
                addApartment = true;
            }

            if (!addApartment)
                continue;

            addApartment = false;
            if (rooms.equals("") || String.valueOf(apartment.getKambaruSkaicius()).equals(rooms)) {
                addApartment = true;
            }

            if (!addApartment)
                continue;

            if (apartment.getKainaUzNakti() >= priceStart && apartment.getKainaUzNakti() <= priceEnd)
                filteredApartments.add(apartment);

        }

        showApartments(filteredApartments);
    }

    /**
     * Adds given apartments to the recycler view  list
     *
     * @param apartments Apartments list
     */
    private void showApartments(List<Apartment> apartments) {
        mAdapter.clear();
        if (apartments.size() == 0)
            Toast.makeText(this, "No apartments found", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(this, apartments.size() + " apartments found", Toast.LENGTH_SHORT).show();

        for (Apartment apartment : apartments) {
            mAdapter.addItem(apartment);
        }
    }
}
