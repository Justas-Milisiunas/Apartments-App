package com.apartmentslt.apartments.owner.activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ContextThemeWrapper;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.ApartmentDeleteDto;
import com.apartmentslt.apartments.models.Complaint;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.services.ApartmentsService;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Calendar;
import java.util.Locale;

public class ApartmentDetailsWithEditButtonActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    private Apartment currentApartment;
    public final static String APARTMENT_DATA_KEY = "com.apartmentslt.apartments.APARTMENT_DATA";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apartment_details_with_edit_button);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();

        this.currentApartment = getCurrentApartment();
        if (this.currentApartment != null) {
            bindData(this.currentApartment);
        }

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddApartmentActivity.class);
                intent.putExtra(AddApartmentActivity.APARTMENT_DATA_KEY, currentApartment);
                intent.putExtra("edit",true);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                //intent.putExtra(ApartmentDetailsActivity.APARTMENT_DATA_KEY, item);
                //Snackbar.make(view, "Here's a Snackbar", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
            }
        });
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        FloatingActionButton fabdel = findViewById(R.id.fabdel);
        fabdel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: ADD DELETE DIALOGUE

                alertDialogBuilder.setTitle("Confirm Delete");
                alertDialogBuilder.setMessage("Are you sure?");
                alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       showConfirmationDialog();
                    }
                });
                alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                alertDialogBuilder.create().show();
            }
        });

    }
    private void showConfirmationDialog() {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        // Makes complaint object
        ApartmentDeleteDto apartmentDeleteDto = new ApartmentDeleteDto(currentApartment.getIdButas(),
                User.getInstance().getIdIsNaudotojas());

        ApartmentsService apartmentsService = retrofit.create(ApartmentsService.class);
        final Call<RequestBody> request = apartmentsService.deleteApartment(apartmentDeleteDto);
        request.enqueue(new Callback<RequestBody>() {
            /**
             * If request was successfully shows message and finishes this activity to go back to apartment details
             * @param call Call
             * @param response Request response
             */
            @Override
            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                if (response.isSuccessful()) {
                    RequestBody savedComplaint = response.body();
                    if (savedComplaint == null) {
                        Toast.makeText(getApplicationContext(), "Could not load saved complaint", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Toast.makeText(getApplicationContext(), "Complaint was successfully sent", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            /**
             * If request was not successful shows error message
             * @param call Call
             * @param t Error
             */
            @Override
            public void onFailure(Call<RequestBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
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
    /**
     * Extracts apartment data from intent
     *
     * @return Apartment data
     */
    public Apartment getCurrentApartment() {
        Intent intent = getIntent();
        Apartment apartment = null;
        if (intent.getExtras() != null) {
            apartment = ((Apartment) intent.getExtras().getSerializable(APARTMENT_DATA_KEY));
            if (apartment == null) {
                Toast.makeText(this, "Could not load apartment", Toast.LENGTH_LONG).show();
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
        ((TextView) findViewById(R.id.address)).setText(apartment.getAdresas());
        ((TextView) findViewById(R.id.price)).setText(apartment.getKainaUzNakti() + " per night");
        ((TextView) findViewById(R.id.size)).setText(apartment.getDydis() + " m²");
        ((TextView) findViewById(R.id.description)).setText(apartment.getAprasas());

        RatingBar ratingBar = findViewById(R.id.ratingBar);
        ratingBar.setRating(apartment.calculateRating());

        ImageView image = findViewById(R.id.apartment_image);
        Glide.with(getApplicationContext())
                .load(apartment.getNuotraukaUrl())
                .error(R.drawable.ic_error)
                .into(image);
        // TODO: Finish binding rating
    }
}
