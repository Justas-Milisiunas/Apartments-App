package com.apartmentslt.apartments.owner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.tenant.activities.FilterDialog;

public class AddApartmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_apartment);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
        boolean editMode = getIntent().getBooleanExtra("edit",false);
        if (editMode) {
            populate();
        }
    }

    //TODO: replace with working version
    void populate() {
        EditText address = findViewById(R.id.address);
        EditText price = findViewById(R.id.price);
        EditText size = findViewById(R.id.size);
        EditText description = findViewById(R.id.description);
        EditText rooms = findViewById(R.id.rooms);

        address.setText("Studentų g. 60, Kaunas");
        price.setText("10€ uz naktį");
        size.setText("Dydis: 45 m2");
        rooms.setText("Kambariai: 2");
        description.setText("temp");
    }
    /**
     * Inflates toolbar menu items for the toolbar
     * @param menu Menu
     * @return true if inflated successfully
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_apartment_menu, menu);
        return true;
    }
    /*private Apartment getCurrentApartment() {
        Intent intent = getIntent();
        Apartment apartment = null;
        if (intent.getExtras() != null) {
            apartment = ((Apartment) intent.getExtras().getSerializable(APARTMENT_DATA_KEY));
            if (apartment == null) {
                Log.d(TAG, "Apartment object is null");
                return null;
            }
        }

        return apartment;
    }

    /**
     * Binds apartments data to components
     *
     * @param apartment Apartment
     */
    /*private void bindData(Apartment apartment) {
        ((TextView) findViewById(R.id.address)).setText(apartment.getAddress());
        // TODO: Finish binding
//        ((TextView) findViewById(R.id.price)).setText();
    }*/

    /**
     * Menu items click listener
     * Shows filter dialog after pressing filter icon
     * @param item Selected menu item
     * @return true if commands initiated successfully
     */
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        boolean editMode = getIntent().getBooleanExtra("edit",false);

        if (item.getItemId() == R.id.action_done) {
            if (editMode) {
                Toast.makeText(getApplicationContext(),"Edited successfully!", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(getApplicationContext(),"Added successfully!", Toast.LENGTH_SHORT).show();

            }
            finish();
        }

        return true;
    }
}
