package com.apartmentslt.apartments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.apartmentslt.apartments.worker.JobsListActivity;
import com.apartmentslt.apartments.owner.activities.ApartmentsListWithAddButtonActivity;
import com.apartmentslt.apartments.tenant.activities.ApartmentsListActivity;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        this.email = findViewById(R.id.emailAddress);
        this.password = findViewById(R.id.password);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();
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
     * TODO: Add validation
     *
     * @param view Current view
     */
    public void login(View view) {
        Intent intent;
        switch (email.getText().toString().toLowerCase()) {
            case "owner":
                // TODO: Start owner main activity
                intent = new Intent(this, ApartmentsListWithAddButtonActivity.class);
                startActivity(intent);
                break;
            case "tenant":
                intent = new Intent(this, ApartmentsListActivity.class);
                startActivity(intent);
                break;
            case "worker":
                intent = new Intent(this, JobsListActivity.class);
                startActivity(intent);
                break;
        }
        finish();
    }
}
