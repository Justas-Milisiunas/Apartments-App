package com.apartmentslt.apartments.profile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.LoginActivity;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.services.UserService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfileActivity extends AppCompatActivity {
    User userData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.apartmentslt.apartments.R.layout.activity_profile);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();

        userData = User.getInstance();
        bindData();
    }

    /**
     * Binds users data to layout components
     */
    @SuppressLint("SetTextI18n")
    private void bindData() {
        TextView name = findViewById(R.id.user_name);
        TextView lastName = findViewById(R.id.user_last_name);
        TextView email = findViewById(R.id.user_email);
        TextView balance = findViewById(R.id.user_balance);
        TextView role = findViewById(R.id.user_role);

        name.setText("Name: " + userData.getVardas());
        lastName.setText("Last Name: " + userData.getPavarde());
        email.setText("Email: " + userData.getElPastas());
        balance.setText("Balance " + userData.getBalansas() + "€");

        String roleText = "Role: ";
        switch (userData.getRole()) {
            case 0:
                roleText += "Owner";
                break;
            case 1:
                roleText += "Tenant";
                break;
            case 2:
                roleText += "Worker";
                break;
        }

        role.setText(roleText);
    }

    public void goEditProfile(View view) {
        Intent profileIntent = new Intent(this, ProfileEditActivity.class);
        startActivity(profileIntent);
    }

    /**
     * Logs out user by removing it's data from local storage
     *
     * @param view Current view
     */
    public void logoutUser(View view) {
        // Deletes user data
        User.logout(getSharedPreferences(User.USER_DATA_FILE, MODE_PRIVATE));

        // Removes all activities from stack, starts login activity
        Intent loginIntent = new Intent(this, LoginActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(loginIntent);
        finish();
    }

    /**
     * Deletes user from the db
     *
     * @param view Current view
     */
    public void removeProfile(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        UserService userService = retrofit.create(UserService.class);
        final Call<ResponseBody> requestCall = userService.deleteProfile(User.getInstance().getIdIsNaudotojas());
        requestCall.enqueue(new Callback<ResponseBody>() {
            /**
             * If request successful logouts user
             * @param call Call
             * @param response Request response
             */
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "Profile removed successfully", Toast.LENGTH_SHORT).show();
                    logoutUser(null);
                } else {
                    Toast.makeText(getApplicationContext(), response.message(), Toast.LENGTH_LONG).show();
                }
            }

            /**
             * If error shows toast message
             * @param call
             * @param t
             */
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onResume() {
        userData = User.getInstance();
        bindData();
        super.onResume();
    }
}
