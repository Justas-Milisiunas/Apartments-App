package com.apartmentslt.apartments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.models.UserRegistrationDto;
import com.apartmentslt.apartments.owner.activities.ApartmentsListWithAddButtonActivity;
import com.apartmentslt.apartments.services.UserService;
import com.apartmentslt.apartments.tenant.activities.ApartmentsListActivity;
import com.apartmentslt.apartments.worker.JobsListActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RegisterActivity extends AppCompatActivity {
    EditText firstName, lastName, emailAddress, password;
    Spinner role;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findLayoutComponents();
        loadSpinnerItems();

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
    }

    /**
     * Finds all layout components used for data
     */
    void findLayoutComponents() {
        this.firstName = findViewById(R.id.firstName);
        this.lastName = findViewById(R.id.lastName);
        this.emailAddress = findViewById(R.id.emailAddress);
        this.password = findViewById(R.id.password);
        this.role = findViewById(R.id.roles);
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
        UserRegistrationDto newUser = new UserRegistrationDto(firstName.getText().toString(), lastName.getText().toString(),
                password.getText().toString(), emailAddress.getText().toString(), role.getSelectedItemPosition());

//         If new user's data invalid shows toast messages
        if (!newUser.Valid()) {
            Toast.makeText(this, "Bad registration information", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register API endpoint request
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        final Call<User> registrationCall = userService.register(newUser);
        registrationCall.enqueue(new Callback<User>() {
            /**
             * If user was created successfully
             * @param call Call
             * @param response Response
             */
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                // If successful
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user == null) {
                        Toast.makeText(getApplicationContext(), "Could not load user's data", Toast.LENGTH_SHORT).show();
                    } else {
                        User.changeData(user);
                        User.saveData(getSharedPreferences(User.USER_DATA_FILE, MODE_PRIVATE));

                        Toast.makeText(getApplicationContext(), "Registered successfully", Toast.LENGTH_SHORT).show();
                        openMainWindow(User.getInstance().getRole());
                    }
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            /**
             * If request was unsuccessful
             * @param call Call
             * @param t error info
             */
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Opens new activity based on user's role
     *
     * @param role Registered user's role
     */
    public void openMainWindow(int role) {
        switch (role) {
            case 0:
                // TODO: Start owner main activity
                Intent ownerIntent = new Intent(this, ApartmentsListWithAddButtonActivity.class);
                startActivity(ownerIntent);
                break;
            case 1:
                // Tenant
                Intent tenantIntent = new Intent(this, ApartmentsListActivity.class);
                startActivity(tenantIntent);
                break;
            case 2:
                // TODO: Start worker main activity
                Intent workerIntent = new Intent(this, JobsListActivity.class);
                startActivity(workerIntent);
                break;
        }

        finish();
    }


}
