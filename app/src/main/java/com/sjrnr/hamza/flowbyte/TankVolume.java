package com.sjrnr.hamza.flowbyte;

import android.graphics.Typeface;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class TankVolume extends AppCompatActivity {
    TextView qty;
    TextView l;
    TextView in_tank_vol;
    Typeface face_normal;
    Typeface face_digit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tank_volume);

        qty = (TextView) findViewById(R.id.textView);
        l = (TextView) findViewById(R.id.textViewl);
        in_tank_vol = (TextView) findViewById(R.id.intank);

        face_normal = Typeface.createFromAsset(getAssets(),
                "Ubahn.ttf");
        face_digit = Typeface.createFromAsset(getAssets(),
                "ds_digit.ttf");

        l.setTypeface(face_normal);
        in_tank_vol.setTypeface(face_normal);
        qty.setTypeface(face_digit);

        if (savedInstanceState != null) {
            // Restore value of members from saved state
            qty.setText(savedInstanceState.getString("litres"));
        }
        else {
            int random = 10 + (int)(Math.random() * 500 + 1);
            qty.setText(Integer.toString(random));
        }


    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("litres", qty.getText().toString());
        super.onSaveInstanceState(outState);
    }
}
