package com.apartmentslt.apartments.worker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.models.Work;
import com.bumptech.glide.Glide;

public class JobDetailsActivity extends AppCompatActivity {
    private static final String TAG = JobDetailsActivity.class.getName();
    public final static String JOB_DATA_KEY = "com.apartmentslt.apartments.JOB_DATA";

    private Work currentWork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_details);

        this.currentWork = getCurrentJob();
        if (this.currentWork != null){
            bindData(this.currentWork);
        }


        setCancelJobButtonVisibility();
        setTakeJobButtonVisibility();
        setDoneWorkButtonVisibility();

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
    }

    /**
     * Shows or hides booking cancel button based on if user rented this apartment
     */
    private void setTakeJobButtonVisibility() {
        Button take = findViewById(R.id.takeJobButton);
        int jobStatus = getJobStatus();

        if (jobStatus != 3) {
            take.setVisibility(View.GONE);
        } else {
            take.setVisibility(View.VISIBLE);
        }
    }

    /**
     * Shows or hides booking cancel button based on if user rented this apartment
     */
    private void setCancelJobButtonVisibility() {
        Button take = findViewById(R.id.jobCancelButton);
        int jobStatus = getJobStatus();
        boolean isUserIn = checkIfUserIsSign();

        if (jobStatus == 2 && isUserIn) {
            take.setVisibility(View.VISIBLE);
        } else {
            take.setVisibility(View.GONE);
        }
    }

    /**
     * Shows or hides booking cancel button based on if user rented this apartment
     */
    private void setDoneWorkButtonVisibility() {
        Button take = findViewById(R.id.jobDoneButton);
        int jobStatus = getJobStatus();
        boolean isUserIn = checkIfUserIsSign();

        if (jobStatus == 1 && isUserIn) {
            take.setVisibility(View.VISIBLE);
        } else {
            take.setVisibility(View.GONE);
        }
    }

    private boolean checkIfUserIsSign(){
        return User.getInstance().getIdIsNaudotojas() == currentWork.getFkValytojasidIsNaudotojas();
    }

    private int getJobStatus(){
        return currentWork.getBusena();
    }

    /**
     * Binds apartments data to components
     *
     * @param job Job
     */
    private void bindData(Work job) {
        String[] splitedDate = String.valueOf(job.getSukurimoData()).split(" ");
        String date = splitedDate[0] + " " + splitedDate[1] + " " + splitedDate[2];

        ((TextView) findViewById(R.id.jobName)).setText(job.getButas().getPavadinimas());
        ((TextView) findViewById(R.id.jobCreationDate)).setText("Creation date: " + date);
        ((TextView) findViewById(R.id.jobAddress)).setText(job.getButas().getMiestas() + " " + job.getButas().getAdresas());
        ((TextView) findViewById(R.id.jobPrice)).setText(String.valueOf(job.getUzmokestis()));
        ((TextView) findViewById(R.id.apartmentSize)).setText(String.valueOf(job.getButas().getDydis()));

//        ImageView image = findViewById(R.id.apartment_image);
//        Glide.with(getApplicationContext())
//                .load(apartment.getNuotraukaUrl())
//                .error(R.drawable.ic_error)
//                .into(image);
    }

    /**
     * Extracts jobs data from intent
     *
     * @return job data
     */
    private Work getCurrentJob() {
        Intent intent = getIntent();
        Work job = null;
        if (intent.getExtras() != null) {
            job = ((Work) intent.getExtras().getSerializable(JOB_DATA_KEY));
            if (job == null) {
                Toast.makeText(this, "Could not load apartment", Toast.LENGTH_LONG).show();
            }
        }
        return job;
    }
}
