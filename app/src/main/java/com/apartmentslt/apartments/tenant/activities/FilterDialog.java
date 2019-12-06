package com.apartmentslt.apartments.tenant.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.apartmentslt.apartments.R;

public class FilterDialog extends DialogFragment {
    private View dialogView;

    /**
     * Inflates custom dialog layout, adds positive and negative buttons
     *
     * @param savedInstanceState savedInstance
     * @return created dialog
     */
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        dialogView = inflater.inflate(R.layout.apartments_list_filtering_dialog, null);
        builder.setView(dialogView)
                .setPositiveButton("Filter", (dialog, which) -> sendFilterOptions())
                .setNegativeButton("Cancel", (dialog, which) -> FilterDialog.this.getDialog().cancel());

        return builder.create();
    }

    /**
     * Loads data to both spinners after view was inflated
     */
    @Override
    public void onStart() {
        loadDialogData();
        super.onStart();
    }

    /**
     * Loads data from apartmentslistactivity for
     * adding cities and room numbers to both spinners
     */
    private void loadDialogData() {
        Spinner citiesSpinner = dialogView.findViewById(R.id.cities_spinner);
        Spinner roomsSpinner = dialogView.findViewById(R.id.rooms_count_spinner);

        ArrayAdapter<String> citiesSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ((ApartmentsListActivity) getActivity()).getAllCities());
        citiesSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citiesSpinner.setAdapter(citiesSpinnerAdapter);

        ArrayAdapter<String> roomsSpinnerAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, ((ApartmentsListActivity) getActivity()).getNumberOfRooms());
        roomsSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roomsSpinner.setAdapter(roomsSpinnerAdapter);
    }

    /**
     * Sends filtering options to apartmentslistactivity
     */
    private void sendFilterOptions() {
        Spinner cities_spinner = dialogView.findViewById(R.id.cities_spinner);
        Spinner rooms_spinner = dialogView.findViewById(R.id.rooms_count_spinner);
        EditText priceFrom = dialogView.findViewById(R.id.from_price);
        EditText priceTo = dialogView.findViewById(R.id.to_price);

        ((ApartmentsListActivity) getActivity()).filterApartments(((String) cities_spinner.getSelectedItem()), ((String) rooms_spinner.getSelectedItem()),
                priceFrom.getText().toString(), priceTo.getText().toString());
    }
}
