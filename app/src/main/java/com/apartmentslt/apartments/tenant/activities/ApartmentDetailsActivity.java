package com.apartmentslt.apartments.tenant.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.RentPeriod;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.services.ApartmentsService;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApartmentDetailsActivity extends AppCompatActivity {
    private static final String TAG = ApartmentDetailsActivity.class.getName();
    public final static String APARTMENT_DATA_KEY = "com.apartmentslt.apartments.APARTMENT_DATA";

    private Apartment currentApartment;
    private int startYear, startMonth, startDay, endYear, endMonth, endDay;

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
     * https://github.com/wdullaer/MaterialDateTimePicker Used dialog library
     *
     * @param view View
     */
    public void bookApartment(View view) {
        Calendar now = Calendar.getInstance();
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                null,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        // Shows dates only from current date to date + 1 year
        datePickerDialog.setMinDate(now);
        now.add(Calendar.YEAR, 1);
        datePickerDialog.setMaxDate(now);

        // Disable days which are rented
        List<Calendar> disabledDays = new ArrayList<>();
        List<Calendar> usersRentDays = new ArrayList<>();
        for (RentPeriod period : currentApartment.getNuomosLaikotarpis()) {
            Calendar start = Calendar.getInstance();
            start.setTime(period.getNuo());

            Calendar end = Calendar.getInstance();
            end.setTime(period.getIki());

            while (start.before(end)) {
                if (period.getFkNuomininkasidIsNaudotojas() == User.getInstance().getIdIsNaudotojas())
                    usersRentDays.add(((Calendar) start.clone()));
                disabledDays.add(((Calendar) start.clone()));
                start.add(Calendar.DAY_OF_MONTH, 1);
            }

            // Adds last interval date
            // TODO: fix this
            disabledDays.add(((Calendar) start.clone()));
        }
        datePickerDialog.setDisabledDays(disabledDays.toArray(new Calendar[disabledDays.size()]));
        datePickerDialog.setHighlightedDays(usersRentDays.toArray(new Calendar[usersRentDays.size()]));

        datePickerDialog.setOkColor(getResources().getColor(R.color.white));
        datePickerDialog.setCancelColor(getResources().getColor(R.color.white));

        datePickerDialog.setOnDateSetListener((view1, year, monthOfYear, dayOfMonth) -> {
            startYear = year;
            startMonth = monthOfYear + 1;
            startDay = dayOfMonth;

            getEndDateDialog(disabledDays);
        });

        datePickerDialog.setTitle("Apartment booking interval start");
        datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    /**
     * Gets end date from second dialog
     * TODO: don't look
     *
     * @param disabledDays All disabled days
     */
    private void getEndDateDialog(List<Calendar> disabledDays) {
        DatePickerDialog datePickerDialog = DatePickerDialog.newInstance(
                null,
                startYear,
                startMonth - 1,
                startDay
        );

        Calendar startDate = Calendar.getInstance();
        startDate.set(startYear, startMonth - 1, startDay);
        datePickerDialog.setMinDate(startDate);

        Calendar endDate = calculateEndDate(disabledDays);
        datePickerDialog.setMaxDate(endDate);
        datePickerDialog.setOnDateSetListener((view2, yearEnd, monthOfYearEnd, dayOfMonthEnd) -> {
            endYear = yearEnd;
            endMonth = monthOfYearEnd + 1;
            endDay = dayOfMonthEnd;

            // Makes apartment booking reservation
            bookApartmentRequest();
        });

        datePickerDialog.setOkColor(getResources().getColor(R.color.white));
        datePickerDialog.setCancelColor(getResources().getColor(R.color.white));

        datePickerDialog.setTitle("Apartment booking interval end");
        datePickerDialog.show(getSupportFragmentManager(), "Datepickerdialog");
    }

    /**
     * Gets interval end date
     *
     * @param list Dates list
     * @return End date or null if not found
     */
    private Calendar calculateEndDate(List<Calendar> list) {
        Calendar date = Calendar.getInstance();
        date.set(startYear, startMonth - 1, startDay);

        Calendar end = Calendar.getInstance();
        end.set(startYear, startMonth - 1, startDay);
        end.add(Calendar.YEAR, 1);

        date.add(Calendar.DAY_OF_MONTH, 1);
        while (date.before(end)) {
            for (Calendar calendar : list) {
                int yy = date.get(Calendar.YEAR);
                int mm = date.get(Calendar.MONTH);
                int dd = date.get(Calendar.DAY_OF_MONTH);
                int yyy = calendar.get(Calendar.YEAR);
                int mmm = calendar.get(Calendar.MONTH);
                int ddd = calendar.get(Calendar.DAY_OF_MONTH);
                if (yy == yyy && mm == mmm && dd == ddd) {
                    date.add(Calendar.DAY_OF_MONTH, -1);
                    return date;
                }
            }

            date.add(Calendar.DAY_OF_MONTH, 1);
        }

        return end;
    }

    /**
     * Makes API request to backend for bo1oking apartment
     */
    private void bookApartmentRequest() {
        Calendar intervalStart = Calendar.getInstance();
        intervalStart.set(Calendar.YEAR, startYear);
        intervalStart.set(Calendar.MONTH, startMonth - 1);
        intervalStart.set(Calendar.DAY_OF_MONTH, startDay);

        Calendar intervalEnd = Calendar.getInstance();
        intervalEnd.set(Calendar.YEAR, endYear);
        intervalEnd.set(Calendar.MONTH, endMonth - 1);
        intervalEnd.set(Calendar.DAY_OF_MONTH, endDay);

        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        RentPeriod reservation = new RentPeriod(intervalStart.getTime(), intervalEnd.getTime(),
                User.getInstance().getIdIsNaudotojas(), currentApartment.getIdButas());

        ApartmentsService apartmentsService = retrofit.create(ApartmentsService.class);
        final Call<RentPeriod> request = apartmentsService.makeReservation(reservation);
        request.enqueue(new Callback<RentPeriod>() {
            /**
             * Makes API request to the apartments backend for apartment reservation
             * if successful reloads apartments data
             * @param call Call
             * @param response Response
             */
            @Override
            public void onResponse(Call<RentPeriod> call, Response<RentPeriod> response) {
                if (response.isSuccessful()) {
                    RentPeriod reservationData = response.body();
                    if (reservationData == null) {
                        Toast.makeText(getApplicationContext(), "Could not load reservation data", Toast.LENGTH_SHORT).show();
                        return;
                    } else {
                        Toast.makeText(getApplicationContext(), "Booking reservation was created successfully", Toast.LENGTH_SHORT).show();
                        // Add new reservation data
                        currentApartment.getNuomosLaikotarpis().add(reservationData);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            /**
             * If reservation could not be made, shows error message
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<RentPeriod> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        ((TextView) findViewById(R.id.price)).setText(apartment.getKainaUzNakti() + " price per night");
        ((TextView) findViewById(R.id.size)).setText(apartment.getDydis() + " m2");
        ((TextView) findViewById(R.id.description)).setText(apartment.getAprasas());

        ImageView image = findViewById(R.id.apartment_image);
        Glide.with(getApplicationContext())
                .load(apartment.getNuotraukaUrl())
                .error(R.drawable.ic_error)
                .into(image);
        // TODO: Finish binding rating
    }
}
