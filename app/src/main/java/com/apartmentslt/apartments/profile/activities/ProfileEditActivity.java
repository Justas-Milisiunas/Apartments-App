package com.apartmentslt.apartments.profile.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.User;

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
}
