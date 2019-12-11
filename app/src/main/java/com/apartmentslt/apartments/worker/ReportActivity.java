package com.apartmentslt.apartments.worker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.apartmentslt.apartments.Appbar;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.ReportGenerateDto;
import com.apartmentslt.apartments.models.User;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class ReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        Appbar toolbar = new Appbar(this, R.id.toolbar, getTitle().toString());
        toolbar.addBackButton();
        toolbar.show();
    }

    public void generateReport(View view) {
        Date from = new GregorianCalendar(2014, Calendar.FEBRUARY, 11).getTime();
        Date to = new GregorianCalendar(2020, Calendar.FEBRUARY, 11).getTime();
        String url = String.format("http://10.0.2.2:5000/api/jobs/report/2019-01-01/2020-01-01/%d", User.getInstance().getIdIsNaudotojas());

        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }
}
