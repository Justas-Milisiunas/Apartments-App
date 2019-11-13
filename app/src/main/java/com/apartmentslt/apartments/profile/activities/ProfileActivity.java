package com.apartmentslt.apartments.profile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.R;


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
}
