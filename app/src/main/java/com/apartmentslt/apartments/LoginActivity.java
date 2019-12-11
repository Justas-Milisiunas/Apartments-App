package com.apartmentslt.apartments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.models.UserLoginDto;
import com.apartmentslt.apartments.services.UserService;
import com.apartmentslt.apartments.worker.JobsListActivity;
import com.apartmentslt.apartments.owner.activities.ApartmentsListWithAddButtonActivity;
import com.apartmentslt.apartments.tenant.activities.ApartmentsListActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    EditText email, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // If user was already logged in
        if (User.loadData(getSharedPreferences(User.USER_DATA_FILE, MODE_PRIVATE))) {
            openMainWindow(User.getInstance().getRole());
        }

        findLayoutComponents();

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();
    }

    /**
     * Finds all layout components used for data
     */
    void findLayoutComponents() {
        this.email = findViewById(R.id.emailAddress);
        this.password = findViewById(R.id.password);
    }

    /**
     * Opens new activity based on user's role
     *
     * @param role Registered user's role
     */
    private void openMainWindow(int role) {
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

    /**
     * Start register activity after pressing ,,Register" button
     *
     * @param view Current view
     */
    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    /**
     * Checks user's login information. If user login information is valid opens
     * activity based on user's role
     *
     * @param view Current view
     */
    public void login(View view) {
        UserLoginDto loginDto = new UserLoginDto(email.getText().toString(), password.getText().toString());

        // If login data is invalid shows toast message
        if (!loginDto.valid()) {
            Toast.makeText(this, "Bad login information", Toast.LENGTH_SHORT).show();
            return;
        }

        // Register API endpoint request
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        final Call<User> loginCall = userService.login(loginDto);
        loginCall.enqueue(new Callback<User>() {
            /**
             * If login was successful
             * @param call Call
             * @param response Response
             */
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User user = response.body();
                    if (user == null) {
                        Toast.makeText(getApplicationContext(), "Could not load user's data", Toast.LENGTH_SHORT).show();
                    } else {
                        User.changeData(user);
                        User.saveData(getSharedPreferences(User.USER_DATA_FILE, MODE_PRIVATE));

                        Toast.makeText(getApplicationContext(), "Logged in successfully", Toast.LENGTH_SHORT).show();
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
}
