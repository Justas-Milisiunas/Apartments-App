package com.apartmentslt.apartments.owner.activities;

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
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.GenericAdapter;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.ApartmentStatus;
import com.apartmentslt.apartments.models.Complaint;
import com.apartmentslt.apartments.profile.activities.ProfileActivity;
import com.apartmentslt.apartments.tenant.activities.ApartmentDetailsActivity;
import com.apartmentslt.apartments.tenant.activities.FilterDialog;
import com.apartmentslt.apartments.tenant.activities.WriteComplaintActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.zip.Inflater;

public class ReadComplaintsActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    GenericAdapter<Complaint> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read_complaints);

        mAdapter = initializeRecyclerView();
        loadData();

        // Add top toolbar
        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();


        // Add bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_toolbar);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottomNavigationView.getMenu().findItem(R.id.navigation_read_complaint).setEnabled(false); // Disable apartments list button
        } else {
            Toast.makeText(this, "Bottom navigation bar could not be loaded", Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * Initializes recycler view for displaying apartments data
     * @return Created recycler view adapter
     */
    private GenericAdapter<Complaint> initializeRecyclerView() {
        RecyclerView mRecyclerView = findViewById(R.id.complaint_list);
        GenericAdapter<Complaint> apartmentsAdapter = new GenericAdapter<Complaint>(this) {
            @Override
            public int getLayoutId() {
                return R.layout.complaint_list_item;
            }

            @Override
            public void onBindData(Complaint model, int position, ItemViewHolder viewHolder) {
                TextView address = ((TextView) viewHolder.getComponent(R.id.address));
                address.setText(model.getPranesimas());
            }

            @Override
            public void onClick(Complaint item, int position) {
                Intent intent = new Intent(getApplicationContext(), ApartmentDetailsActivity.class);
               // intent.putExtra(ApartmentDetailsActivity.APARTMENT_DATA_KEY, item);
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
//        Complaint demo = new Complaint(
//                "Jonas", "Studentu g 60, Kaunas", "Neveikia vandentiekis");
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
                finish();

                return true;
            case R.id.navigation_read_complaint:
;
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
        menuInflater.inflate(R.menu.profile_menu, menu);
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
        if (item.getItemId() == R.id.action_profile) {
            Intent intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
        }

        return true;
    }
}
