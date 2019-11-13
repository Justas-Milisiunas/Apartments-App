package com.apartmentslt.apartments.profile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.R;

public class ProfileRemoveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_remove);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();
    }
}
