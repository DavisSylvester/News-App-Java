package com.sylvesterllc.newapps1;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;

import static android.preference.PreferenceManager.getDefaultSharedPreferences;

public class SettingsActivity extends AppCompatActivity {

    String searchTextPreference;
    SharedPreferences sharedPref;
    private SharedPreferences.OnSharedPreferenceChangeListener prefListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sharedPref = getSharedPreferences("settings", Context.MODE_PRIVATE);

        searchTextPreference = sharedPref.getString("FirstName", "");

        Log.d("HELP", sharedPref.getString("FirstName", ""));

        sharedPref.edit().putString("FirstName", searchTextPreference).commit();


        Log.d("HELP", sharedPref.getString("FirstName", ""));

    }



    public static class SettingsPreferenceFragment extends PreferenceFragment {

        private String oldValue;
        private EditTextPreference fName;
        private SharedPreferences.OnSharedPreferenceChangeListener listener;

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preference_screen_setting);

            fName = (EditTextPreference) findPreference("FirstName");

            createListener();

        }

        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
                                              String key) {
            updatePrefSummary(findPreference(key));
        }

        private void updatePrefSummary(android.preference.Preference p) {

//            Log.d("HELP", p.getKey("FirstName").toString());
//            Context ctx = getContext();
//            ctx.getSharedPreferences("settings", Context.MODE_PRIVATE)
//                    .edit().putString("FirstName", searchTextPreference).commit();FirstName
        }

        private void createListener() {
            listener = new SharedPreferences.OnSharedPreferenceChangeListener() {
                @Override
                public void onSharedPreferenceChanged(
                        SharedPreferences sharedPreferences, String key) {
                    String value = sharedPreferences.getString("FirstName", "");


                        fName.setText(value);
                        setPreferenceScreen(null);
                        addPreferencesFromResource(R.xml.preference_screen_setting);

                }
            };
            getDefaultSharedPreferences(getActivity().getApplicationContext())
                    .registerOnSharedPreferenceChangeListener(listener);
        }

    }






}
