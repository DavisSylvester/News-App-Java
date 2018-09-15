package com.sylvesterllc.newapps1.Fragments;


import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.sylvesterllc.newapps1.R;

public class SettingPreferenceFragment extends PreferenceFragmentCompat {
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        super.onCreate(savedInstanceState);
//    }

    @Override
    public void onCreatePreferences(Bundle bundle, String s) {
        addPreferencesFromResource(R.xml.preference_settings);
    }
}
