package com.apartmentslt.apartments.owner.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Complaint;
import com.apartmentslt.apartments.models.ReportGenerateDto;
import com.apartmentslt.apartments.models.SearchOptions;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.profile.activities.ProfileActivity;
import com.apartmentslt.apartments.services.ApartmentsService;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class GenerateSummaryActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    CalendarView calendar;
    Button fromButton;
    Button toButton;
    Button generateButton;
    TextView fromText;
    TextView toText;
    CheckBox checkBox;
    int years, months, days;
    String from, to;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generate_summary);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();
        calendar = findViewById(R.id.calendarView);
        fromButton = findViewById(R.id.from_button);
        toButton = findViewById(R.id.to_button);
        generateButton = findViewById(R.id.generate_button);
        fromText = findViewById(R.id.from_text);
        toText = findViewById(R.id.to_text);
        checkBox = findViewById(R.id.checkBox);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                years = year;
                months = month;
                days = dayOfMonth;

            }
        });

        fromButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long mills = calendar.getDate();
                //fromText.setText(getDate(mills, "yyyy-MM-dd"));
                from = years+"-"+months+"-"+days;
                fromText.setText(from);
            }
        });
        toButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long mills = calendar.getDate();
                //toText.setText(getDate(mills, "yyyy-MM-dd"));
                to = years+"-"+months+"-"+days;
                toText.setText(to);

            }
        });

        generateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from != null && to != null) {
                    if (checkBox.isChecked()) {
                        Gson gson = new GsonBuilder()
                                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                                .create();

                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(BuildConfig.API_URL)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

                        ReportGenerateDto searchOptions = new ReportGenerateDto(from, to, User.getInstance().getIdIsNaudotojas(), true );


                        ApartmentsService apartmentsService = retrofit.create(ApartmentsService.class);
                        final Call<RequestBody> requestCall = apartmentsService.generateReport(searchOptions);

                        requestCall.enqueue(new Callback<RequestBody>() {
                            /**
                             * If request to apartments API was successful loads apartments data
                             * @param call Call
                             * @param response Response
                             */
                            @Override
                            public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                                if (response.isSuccessful()) {
                                    RequestBody complaints = response.body();
                                    if (complaints == null) {
                                        Toast.makeText(getApplicationContext(), "Could not send email, parameters null", Toast.LENGTH_SHORT).show();
                                        return;
                                    }

                                } else {
                                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                                }
                            }

                            /**
                             * If request to apartments API was unsuccessful shows error message
                             * @param call Call
                             * @param t exception
                             */
                            @Override
                            public void onFailure(Call<RequestBody> call, Throwable t) {
                                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        Toast.makeText(getApplicationContext(), "Forwarded to email service", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        String url = "http://192.168.8.107:55089/api/apartments/report/" + from + "/" +to+"/" + User.getInstance().getIdIsNaudotojas() +"/false";

                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        startActivity(i);

                        Toast.makeText(getApplicationContext(), "Show download window", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

        // Add bottom navigation bar
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_toolbar);
        if (bottomNavigationView != null) {
            bottomNavigationView.setOnNavigationItemSelectedListener(this);
            bottomNavigationView.getMenu().findItem(R.id.navigation_summary).setEnabled(false); // Disable apartments list button
        } else {
            Toast.makeText(this, "Bottom navigation bar could not be loaded", Toast.LENGTH_SHORT).show();
        }

    }
    public static String getDate(long milliSeconds, String dateFormat)
    {
        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat(dateFormat);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(milliSeconds);
        return formatter.format(calendar.getTime());
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.navigation_apartments_list:
                finish();

                return true;
            case R.id.navigation_read_complaint:
                intent = new Intent(this, ReadComplaintsActivity.class);
                startActivity(intent);
                return true;
            case R.id.navigation_summary:

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
