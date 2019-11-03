package com.apartmentslt.apartments;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

    public void register(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void login(View view) {
        switch (email.getText().toString().toLowerCase()) {
            case "owner":
                // TODO: Start owner main activity

                finish();
                break;
            case "tenant" :
                Intent intent = new Intent(this, ApartmentsListActivity.class);
                startActivity(intent);
                finish();
                break;
            case "worker" :
                // TODO: Start worker main activity

                finish();
                break;
        }
    }
}
