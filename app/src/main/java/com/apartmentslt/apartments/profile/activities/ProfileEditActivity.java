package com.apartmentslt.apartments.profile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.Apartment;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.models.UserUpdateDto;
import com.apartmentslt.apartments.services.ApartmentsService;
import com.apartmentslt.apartments.services.UserService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProfileEditActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();
        toolbar.addBackButton();

        bindData();
    }

    /**
     * Binds logged in user's data to layout's components
     */
    private void bindData() {
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText emailAddress = findViewById(R.id.emailAddress);

        User user = User.getInstance();

        firstName.setText(user.getVardas());
        lastName.setText(user.getPavarde());
        emailAddress.setText(user.getElPastas());
    }

    public void edit(View view) {
        EditText firstName = findViewById(R.id.firstName);
        EditText lastName = findViewById(R.id.lastName);
        EditText emailAddress = findViewById(R.id.emailAddress);
        EditText password = findViewById(R.id.password);
        EditText newPassword = findViewById(R.id.new_password);

        User user = User.getInstance();
        UserUpdateDto userUpdateDto = new UserUpdateDto(
                user.getIdIsNaudotojas(), firstName.getText().toString(),
                emailAddress.getText().toString(), lastName.getText().toString(),
                password.getText().toString(), newPassword.getText().toString());

        if (!userUpdateDto.valid()) {
            Toast.makeText(this, "Bad profile data", Toast.LENGTH_SHORT).show();
            return;
        }

        sendUpdateRequest(userUpdateDto);
    }

    private void sendUpdateRequest(UserUpdateDto updateDto) {
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss")
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.API_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        UserService userService = retrofit.create(UserService.class);
        final Call<User> requestCall = userService.updateProfile(updateDto);
        requestCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    User savedData = response.body();
                    if (savedData == null) {
                        Toast.makeText(getApplicationContext(), "Profile information could not be saved", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    User.changeData(savedData);
                    User.saveData(getSharedPreferences(User.USER_DATA_FILE, MODE_PRIVATE));
                    Toast.makeText(getApplicationContext(), "Your profile information was updated", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Profile information could not be saved", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
