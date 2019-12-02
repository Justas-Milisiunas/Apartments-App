package com.apartmentslt.apartments.profile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.LoginActivity;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.User;


public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.apartmentslt.apartments.R.layout.activity_profile);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();
    }

    public void goEditProfile(View view) {
        Intent profileIntent = new Intent(this, ProfileEditActivity.class);
        startActivity(profileIntent);
    }

    public void goRemoveProfile(View view) {
        Intent profileIntent = new Intent(this, ProfileRemoveActivity.class);
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
}
