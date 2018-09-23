package com.sylvesterllc.newapps1;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class SettingssActivity extends AppCompatActivity {

    private String TAG = "SETTING_ACTIVITY";
    private EditText editFirstName;
    private TextView result;
    private String firstName = "";
    SharedPreferences sharedPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        sharedPref =  getSharedPreferences("settings", Context.MODE_PRIVATE);
        editFirstName = findViewById(R.id.txtSearchText);
        result = findViewById(R.id.txtReturnValue);
        firstName = sharedPref.getString("FirstName", "");

        editFirstName.setText(firstName);
        result.setText(firstName);

    }

    public void btnSave(View view) {

        sharedPref.edit().putString("FirstName", editFirstName.getText().toString()).commit();
        result.setText(editFirstName.getText().toString());
    }

    public void btnPrevious(View view) {
        finish();
    }
}
