package com.apartmentslt.apartments.tenant.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.GenericAdapter;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.ApartmentStatus;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.zip.Inflater;

public class ApartmentsListActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    GenericAdapter<Apartment> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);

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
                address.setText(model.getAddress());
            }

            @Override
            public void onClick(Apartment item, int position) {
                Intent intent = new Intent(getApplicationContext(), ApartmentDetailsActivity.class);
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
        Apartment demo = new Apartment(50, 3, 15, "Studentu g 60, Kaunas", ApartmentStatus.EMPTY, "Good apartment");
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.apartments_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_filter) {
            FilterDialog filterDialog = new FilterDialog();
            filterDialog.show(getSupportFragmentManager(), "FilterDialogFragment");
        }

        return true;
    }
}
