package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class EditCityFragment extends DialogFragment {
    private City temp_city;
    private City editingCity;
    @NonNull
    public static EditCityFragment newInstance(City city) { // from lab instructions
        Bundle args = new Bundle();
        args.putSerializable("city", city);
//        temp_city = city;
        EditCityFragment fragment = new EditCityFragment();
        fragment.setArguments(args);
        return fragment;
    }

    interface EditCityDialogListener {
        default void editCity(City city, String newCityName, String newProvince){
            city.setName(newCityName);
            city.setProvince(newProvince);
        };
    }
    private EditCityDialogListener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof EditCityDialogListener) {
            listener = (EditCityDialogListener) context;
        } else {
            throw new RuntimeException(context + " must implement EditCityDialogListener");
        }
    }
    @NonNull
    @Override // check over this function!
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_edit_city, null);
        EditText editCityName = view.findViewById(R.id.edit_text_city_text);
        EditText editProvinceName = view.findViewById(R.id.edit_text_province_text);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        // set the text before returning anything!
        if (getArguments()!=null){
            editingCity = (City) getArguments().getSerializable("city"); // get that city that was clicked
            assert editingCity != null;
            editCityName.setText(editingCity.getName()); // set the texts into place
            editProvinceName.setText(editingCity.getProvince());
        }
        return builder
                .setView(view)
                .setTitle("Edit city")
                .setNegativeButton("Cancel", null)
                .setPositiveButton("Edit", (dialog, which) -> {
                    String provinceName = editProvinceName.getText().toString();
                    String cityName = editCityName.getText().toString();
                    assert getArguments() != null;
                    editingCity = (City) getArguments().getSerializable("city");
                    assert editingCity != null;
                    listener.editCity(editingCity, editCityName.getText().toString(), editProvinceName.getText().toString());
//                    listener.editCity(editingCity, cityName, provinceName);
                })
                .create();
    }
}