package com.apartmentslt.apartments;

import android.content.Context;
import android.os.Build;
import android.view.View;

import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;

public class Appbar {
    private Context context;
    private Toolbar toolbar;

    public Appbar(Context context, int toolbarId, String title) {
        this.context = context;

        this.toolbar = ((AppCompatActivity) context).findViewById(toolbarId);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.toolbar.setTitle(title);
        }
    }

    /**
     * Shows toolbar at the top
     */
    public void show() {
        ((AppCompatActivity) context).setSupportActionBar(this.toolbar);
    }

    /**
     * Adds go back arrow button
     */
    void addBackButton() {
        show();
        if (((AppCompatActivity) this.context).getSupportActionBar() != null) {
            ((AppCompatActivity) this.context).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            ((AppCompatActivity) this.context).getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }
}
