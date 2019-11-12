package com.apartmentslt.apartments;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.models.Apartment;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Locale;

public class ApartmentDetailsActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
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

    /**
     * Opens calendar to select booking dates after book button click
     * https://stackoverflow.com/questions/39916178/how-to-show-datepickerdialog-on-button-click DatePickerDialog
     *
     * @param view View
     */
    public void bookApartment(View view) {
        // TODO: Change DatePickerDialog to other for selecting date ranges https://github.com/heysupratim/MaterialDateRangePicker

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        datePickerDialog.show();
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

    /**
     * After date select
     *
     * @param view       View
     * @param year       Selected year
     * @param month      Selected month
     * @param dayOfMonth Selected day
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Toast.makeText(this, String.format(Locale.getDefault(), "Year: %d Month: %d Day: %d",
                year, month, dayOfMonth), Toast.LENGTH_LONG).show();
    }
}
