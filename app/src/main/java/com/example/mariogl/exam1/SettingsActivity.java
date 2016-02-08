package com.example.mariogl.exam1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.mariogl.exam1.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    Spinner spinnerFrom;
    Spinner spinnerTo;
    @Override
    protected void onResume() {
        super.onResume();
        editor = preferences.edit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        preferences = getBaseContext().getSharedPreferences(CurrencyPreferences.CURRENCY_PREFS_NAME, Context.MODE_PRIVATE);

        spinnerFrom = (Spinner) findViewById(R.id.spinnerCurrencyFrom);
        spinnerTo = (Spinner) findViewById(R.id.spinnerCurrencyTo);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.currency_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        String currencyFromValue = preferences.getString(CurrencyPreferences.CURRENCY_FROM_KEY, CurrencyPreferences.CURRENCY_FROM_DEFAULT);
        String currencyToValue = preferences.getString(CurrencyPreferences.CURRENCY_TO_KEY, CurrencyPreferences.CURRENCY_TO_DEFAULT);

        spinnerFrom.setSelection(adapter.getPosition(currencyFromValue));
        spinnerTo.setSelection(adapter.getPosition(currencyToValue));

        spinnerFrom.setOnItemSelectedListener(this);
        spinnerTo.setOnItemSelectedListener(this);
    }

    public void returnToMainScreen(View view) {
        editor.commit();
        Intent goToMain = new Intent(this, CurrencyActivity.class);
        startActivity(goToMain);
    }

    public void onItemSelected(AdapterView<?> parent, View v, int position, long id) {
        AppCompatTextView view = (AppCompatTextView)v;
        if(parent == spinnerFrom) {
            editor.putString(CurrencyPreferences.CURRENCY_FROM_KEY, view.getText().toString());
        }else {
            editor.putString(CurrencyPreferences.CURRENCY_TO_KEY, view.getText().toString());
        }
    }

    public void onNothingSelected(AdapterView<?> arg0) {

    }
}
