package com.apartmentslt.apartments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.apartmentslt.apartments.tenant.activities.ApartmentsListActivity;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loadSpinnerItems();

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
    }

    /**
     * Loads dropdown menu items
     */
    void loadSpinnerItems() {
        Spinner spinner = findViewById(R.id.roles);
        String[] items = new String[]{"Owner", "Tenant", "Worker"};

        // TODO: Load roles from API
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);
    }

    /**
     * Caller after register button press
     *
     * @param view View
     */
    public void register(View view) {
        Spinner spinner = view.findViewById(R.id.roles);
        switch (spinner.getSelectedItemPosition()) {
            case 0:
                // TODO: Start owner main activity

                break;
            case 1:
                // Tenant
                Intent intent = new Intent(this, ApartmentsListActivity.class);
                startActivity(intent);
                break;
            case 2:
                // TODO: Start worker main activity

                break;
        }

        finish();
    }
}
