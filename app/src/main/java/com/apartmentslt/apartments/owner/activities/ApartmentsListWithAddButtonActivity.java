package com.apartmentslt.apartments.owner.activities;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.GenericAdapter;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.ApartmentStatus;
import com.apartmentslt.apartments.tenant.activities.ApartmentDetailsActivity;
import com.apartmentslt.apartments.tenant.activities.FilterDialog;
import com.apartmentslt.apartments.profile.activities.ProfileActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ApartmentsListWithAddButtonActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    GenericAdapter<Apartment> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list_with_add);

        mAdapter = initializeRecyclerView();
        loadData();

        // Add top toolbar
        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddApartmentActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //intent.putExtra(ApartmentDetailsActivity.APARTMENT_DATA_KEY, item);
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                       // .setAction("Action", null).show();
            }
        });

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
     * @return Created recycler view adapter
     */
    private GenericAdapter<Apartment> initializeRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.apartments_list);
        GenericAdapter<Apartment> apartmentsAdapter = new GenericAdapter<Apartment>(this) {
            @Override
            public int getLayoutId() {
                return R.layout.apartments_list_item;
            }

            @Override
            public void onBindData(Apartment model, int position, ItemViewHolder viewHolder) {
                TextView address = ((TextView) viewHolder.getComponent(R.id.address));
                address.setText(model.getAdresas());
            }

            @Override
            public void onClick(Apartment item, int position) {
                Intent intent = new Intent(getApplicationContext(), ApartmentDetailsWithEditButtonActivity.class);
                intent.putExtra(ApartmentDetailsActivity.APARTMENT_DATA_KEY, item);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                getBaseContext().startActivity(intent);
            }
        };

        mRecyclerView.setAdapter(apartmentsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        return apartmentsAdapter;
    }

    /**
     * Loads and adds data to the list
     * TODO: Load data from backend API
     */
    private void loadData() {
//        Apartment demo = new Apartment(50, 3, 15, "Studentų g 60, Kaunas", ApartmentStatus.EMPTY, "Good apartment");
//        this.mAdapter.addItem(demo);
//        this.mAdapter.addItem(demo);
//        this.mAdapter.addItem(demo);
//        this.mAdapter.addItem(demo);
//        this.mAdapter.addItem(demo);
//        this.mAdapter.addItem(demo);
    }

    /**
     * Bottom navigation bar clicked menu items listener
     * TODO: Add bottom navigation bar functionality
     *
     * @param menuItem Clicked menu item
     * @return true
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;

        switch (menuItem.getItemId()) {
            case R.id.navigation_apartments_list:
                return true;
            case R.id.navigation_read_complaint:
                intent = new Intent(this, ReadComplaintsActivity.class);
                startActivity(intent);
                return true;
            case R.id.navigation_summary:
                intent = new Intent(this, GenerateSummaryActivity.class);
                startActivity(intent);
                return true;
        }
        return false;
    }

    /**
     * Inflates toolbar menu items for the toolbar
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
}
