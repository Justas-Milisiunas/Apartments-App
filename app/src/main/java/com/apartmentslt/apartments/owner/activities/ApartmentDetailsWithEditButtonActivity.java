package com.apartmentslt.apartments.owner.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Calendar;
import java.util.Locale;

public class ApartmentDetailsWithEditButtonActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details_with_edit_button);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddApartmentActivity.class);
                intent.putExtra("edit",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //intent.putExtra(ApartmentDetailsActivity.APARTMENT_DATA_KEY, item);
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
            }
        });

    }
    public void bookApartment(View view) {
        // TODO: Change DatePickerDialog to other for selecting date ranges https://github.com/heysupratim/MaterialDateRangePicker

        DatePickerDialog datePickerDialog = new DatePickerDialog(this, this, Calendar.YEAR, Calendar.MONTH, Calendar.DAY_OF_MONTH);
        datePickerDialog.show();
    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Toast.makeText(this, String.format(Locale.getDefault(), "Year: %d Month: %d Day: %d",
                year, month, dayOfMonth), Toast.LENGTH_LONG).show();
    }
}
