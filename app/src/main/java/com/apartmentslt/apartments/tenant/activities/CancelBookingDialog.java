package com.apartmentslt.apartments.tenant.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.apartmentslt.apartments.BuildConfig;
import com.apartmentslt.apartments.R;
import com.apartmentslt.apartments.models.CancelBooking;
import com.apartmentslt.apartments.models.RentPeriod;
import com.apartmentslt.apartments.models.User;
import com.apartmentslt.apartments.services.ApartmentsService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class CancelBookingDialog extends DialogFragment {
    private View dialogView;
    private List<RentPeriod> allRents;

    /**
     * Inflates custom dialog layout, adds positive and negative buttons
     *
     * @param savedInstanceState savedInstance
     * @return created dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.booking_cancel_dialog, null);
        builder.setView(dialogView)
                .setPositiveButton("Remove booking", (dialog, which) -> showConfirmationDialog())
                .setNegativeButton("Cancel", (dialog, which) -> CancelBookingDialog.this.getDialog().cancel());

        return builder.create();
    }

    /**
     * Shows confirmation dialog
     */
    private void showConfirmationDialog() {
        Spinner rentsSpinner = dialogView.findViewById(R.id.bookings_spinner);
        RentPeriod selected = allRents.get(rentsSpinner.getSelectedItemPosition());

        ((ApartmentDetailsActivity) getActivity()).cancelBookingConfirmation(selected);
    }

    /**
     * Loads rent periods to recycler view list
     */
    @Override
    public void onStart() {
        allRents = loadRentPeriods();
        addDataToSpinner();
        // TODO add items to adapter
        super.onStart();
    }

    /**
     * Adds all user's rents dates to the spinner
     */
    private void addDataToSpinner() {
        Spinner rentsSpinner = dialogView.findViewById(R.id.bookings_spinner);

        String[] rents = new String[allRents.size()];
        for (int i = 0; i < rents.length; i++) {
            String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(allRents.get(i).getNuo());
            String toDate = new SimpleDateFormat("yyyy-MM-dd").format(allRents.get(i).getIki());

            String item = String.format("From %s to %s", fromDate, toDate);
            rents[i] = item;
        }

        ArrayAdapter<String> rentsAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, rents);
        rentsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        rentsSpinner.setAdapter(rentsAdapter);
    }


    /**
     * Loads rent periods data from parent activity
     */
    private List<RentPeriod> loadRentPeriods() {
        return ((ApartmentDetailsActivity) getActivity()).getAllUsersBookings();
    }
}
