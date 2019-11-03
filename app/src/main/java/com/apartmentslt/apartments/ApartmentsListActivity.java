package com.apartmentslt.apartments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.ApartmentStatus;

import java.util.LinkedList;

public class ApartmentsListActivity extends AppCompatActivity {
    GenericAdapter<Apartment> mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartments_list);

        mAdapter = initializeRecyclerView();
        loadData();

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();
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
                Toast.makeText(getApplicationContext(), item.getAddress(), Toast.LENGTH_LONG).show();
            }
        };

        mRecyclerView.setAdapter(apartmentsAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        return apartmentsAdapter;
    }

    private void loadData() {
        Apartment demo = new Apartment(50, 3, 15, "Studentu g 60, Kaunas", ApartmentStatus.EMPTY);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
        this.mAdapter.addItem(demo);
    }
}