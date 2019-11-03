package com.apartmentslt.apartments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.apartmentslt.apartments.models.Apartment;

public class ApartmentDetailsActivity extends AppCompatActivity {
    private static final String TAG = ApartmentDetailsActivity.class.getName();
    public final static String APARTMENT_DATA_KEY = "com.apartmentslt.apartments.APARTMENT_DATA";

    private Apartment currentApartment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details);

        this.currentApartment = getCurrentApartment();
        if (this.currentApartment != null) {
            bindData(this.currentApartment);
        }

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
    }

    public void bookApartment(View view) {
    }

    /**
     * Extracts apartment data from intent
     *
     * @return Apartment data
     */
    private Apartment getCurrentApartment() {
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
    private void bindData(Apartment apartment) {
        ((TextView) findViewById(R.id.address)).setText(apartment.getAddress());
        // TODO: Finish binding
//        ((TextView) findViewById(R.id.price)).setText();
    }
}
