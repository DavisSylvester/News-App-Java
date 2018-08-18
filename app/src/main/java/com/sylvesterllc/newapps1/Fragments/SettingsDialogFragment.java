package com.sylvesterllc.newapps1.Fragments;


import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.sylvesterllc.newapps1.R;

import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingsDialogFragment extends DialogFragment {

    private String TAG = "SETTING_DIALOG_FRAGMENT";
    private EditText editFirstName;
    private String firstName = "";
    SharedPreferences sharedPref;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        sharedPref = getActivity().getSharedPreferences("settings", Context.MODE_PRIVATE);

        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.fragment_settings_dialog, null);

        editFirstName = view.findViewById(R.id.txtFirstName);

        firstName = sharedPref.getString("FirstName", "");

        editFirstName.setText(firstName);

        // Map<String> aaa = sharedPref.getAll();

        builder.setView(view)

            .setPositiveButton(R.string.save_setting, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    Log.d(TAG, "Save button clicked");

                    // SharedPreferences.Editor editor = sharedPref.edit();

                    sharedPref.edit().putString("FirstName", editFirstName.getText().toString()).commit();

                    Log.d(TAG, editFirstName.getText().toString());


                }
            })

            .setNegativeButton(R.string.cancel_setting, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    Log.d(TAG, "Cancel button clicked");
                }
            });




        return builder.create();
    }

}
