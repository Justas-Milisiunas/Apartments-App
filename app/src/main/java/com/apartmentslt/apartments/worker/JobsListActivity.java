package com.apartmentslt.apartments.worker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.ProfileActivity;
import com.apartmentslt.apartments.R;

public class JobsListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jobs_list);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.show();
    }

    public void goProfile(View view) {
        Intent profileIntent = new Intent(this, ProfileActivity.class);
        startActivity(profileIntent);

    }

    public void goDetails(View view) {
        Intent profileIntent = new Intent(this, JobDetailsActivity.class);
        startActivity(profileIntent);

    }

    public void goHistory(View view) {
        Intent profileIntent = new Intent(this, JobsHistoryActivity.class);
        startActivity(profileIntent);

    }

    public void goReport(View view) {
        Intent profileIntent = new Intent(this, ReportActivity.class);
        startActivity(profileIntent);
    }
}
