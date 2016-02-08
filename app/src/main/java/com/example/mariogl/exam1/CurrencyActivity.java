package com.example.mariogl.exam1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CurrencyActivity extends ActionBarActivity {

    SharedPreferences preferences;
    String currencyFrom = "";
    String currencyTo = "";
    EditText currencyEditText;

    public void goToSettings(View view) {
        Intent goToSettings = new Intent(this, SettingsActivity.class);
        startActivity(goToSettings);
    }

    public void convertMyMoney(View view) {
        StringBuilder builder = new StringBuilder();
        String currencytext = currencyEditText.getText().toString();
        if(currencytext == null || currencytext.equals("") || currencytext.length() < 1) {
            builder.append("Value is empty!");
        }else {
            Double amount = Double.parseDouble(currencytext);
            Double conversionRate = 0.0;
            Double amountExchange = 0.0;
            if (currencyFrom.equals(CurrencyPreferences.CURRENCY_USD)) {
                conversionRate = CurrencyPreferences.AMMOUNT_USD;
            }else if(currencyFrom.equals(CurrencyPreferences.CURRENCY_EUR)) {
                conversionRate = CurrencyPreferences.AMMOUNT_EUR;
            }else {
                conversionRate = CurrencyPreferences.AMMOUNT_MXN;
            }
            if (currencyTo.equals(CurrencyPreferences.CURRENCY_USD)) {
                amountExchange = CurrencyPreferences.AMMOUNT_USD;
            }else if(currencyTo.equals(CurrencyPreferences.CURRENCY_EUR)) {
                amountExchange = CurrencyPreferences.AMMOUNT_EUR;
            }else {
                amountExchange = CurrencyPreferences.AMMOUNT_MXN;
            }

            amount = (amount * amountExchange) / conversionRate;
            builder.append(amount);
        }
        Toast.makeText(getBaseContext(), builder.toString(), Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_currency);

        currencyEditText = (EditText) findViewById(R.id.currency_textfield);

        preferences = getBaseContext().getSharedPreferences(CurrencyPreferences.CURRENCY_PREFS_NAME, Context.MODE_PRIVATE);
        currencyFrom = preferences.getString(CurrencyPreferences.CURRENCY_FROM_KEY, CurrencyPreferences.CURRENCY_FROM_DEFAULT);
        currencyTo = preferences.getString(CurrencyPreferences.CURRENCY_TO_KEY, CurrencyPreferences.CURRENCY_TO_DEFAULT);

    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("RESUME", "ON RESUME");
        TextView currencyFromView = (TextView) findViewById(R.id.currency_from);
        TextView currencyToView = (TextView) findViewById(R.id.currency_to);

        currencyFromView.setText(currencyFrom);
        currencyToView.setText(currencyTo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_currency, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
