package com.sjrnr.hamza.flowbyte;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.Set;
import java.util.Timer;

public class MainActivity extends AppCompatActivity {
    public static int REQUEST_ENABLE_BT = 5;
    Button go;
    Spinner spinner_make;
    Spinner spinner_year;
    BluetoothAdapter bluetoothAdapter;
    RelativeLayout load_indicator;
    MyView bouncer;
    TextView load_description;
    Typeface face;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        bouncer = (MyView) findViewById(R.id.customView);
        spinner_make = (Spinner) findViewById(R.id.spinner_make);
        load_description = (TextView) findViewById(R.id.load_description);
        load_indicator = (RelativeLayout) findViewById(R.id.loading);
        face = Typeface.createFromAsset(getAssets(),
                "Ubahn.ttf");

        load_description.setTypeface(face);

        HomeArrayAdapter adapter_make = new HomeArrayAdapter(this, R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.car_model), face);
        adapter_make.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner_year = (Spinner) findViewById(R.id.spinner_year);

        HomeArrayAdapter adapter_year = new HomeArrayAdapter(this, R.layout.simple_spinner_item,
                getResources().getStringArray(R.array.car_year), face);
        adapter_year.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        try {
            spinner_make.setAdapter(adapter_make);
            spinner_year.setAdapter(adapter_year);
        } catch (Exception e) {
            e.printStackTrace();
        }

        go = (Button) findViewById(R.id.button);

        try {
            go.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (spinner_make.getSelectedItemPosition() == 0 && spinner_year.getSelectedItemPosition() == 0){
                        Snackbar.make(view, getString(R.string.home_warning_both), Snackbar.LENGTH_SHORT).show();
                    }
                    else if (spinner_make.getSelectedItemPosition() == 0 && spinner_year.getSelectedItemPosition() != 0) {
                        Snackbar.make(view, getString(R.string.home_warning_model), Snackbar.LENGTH_SHORT).show();
                    }
                    else if (spinner_year.getSelectedItemPosition() == 0 && spinner_make.getSelectedItemPosition() != 0){
                        Snackbar.make(view, getString(R.string.home_warning_year), Snackbar.LENGTH_SHORT).show();
                    }
                    else if (spinner_make.getSelectedItemPosition() != 0 && spinner_year.getSelectedItemPosition() != 0){
                        checkBluetooth(view);
                    }


                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void checkBluetooth(View view){

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (bluetoothAdapter == null){
            Snackbar.make(view, getString(R.string.home_warning_bluetooth_unsupported), Snackbar.LENGTH_SHORT).show();
        }
        else{
            if (!bluetoothAdapter.isEnabled()){
                Snackbar.make(view, getString(R.string.bluetooth_enable_msg), Snackbar.LENGTH_SHORT)
                        .setAction(getString(R.string.enable_bt_txt), new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                                startActivityForResult(intent, REQUEST_ENABLE_BT);
                            }
                        }).show();
            }
            else{
                beginLoad();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT){
            if (resultCode == RESULT_OK){
                //startDiscovery();
                beginLoad();
            }
            else if (resultCode == RESULT_CANCELED){
                Snackbar.make(go, getString(R.string.error_bt_enable), Snackbar.LENGTH_SHORT).show();
            }
        }
    }

    public void beginLoad(){
        load_indicator.setVisibility(View.VISIBLE);
        bouncer.startAnimation();
        load_description.setText(getString(R.string.reticulating_splines));


        Handler handler1 = new Handler();
        // Define the code block to be executed
        Runnable runnableCode1 = new Runnable() {
            @Override
            public void run() {
                // Do something here on the main thread
                load_description.setText(getString(R.string.acquiring_car_info));

                Handler handler2 = new Handler();
                Runnable runnableCode2 = new Runnable() {
                    @Override
                    public void run() {
                        load_description.setText(getString(R.string.sync_to_vehicle));
                        Handler handler3 = new Handler();
                        Runnable runnableCode3 = new Runnable() {
                            @Override
                            public void run() {
                                bouncer.stopAnimation();
                                load_indicator.setVisibility(View.GONE);
                                showQuatity();
                            }
                        };
                        handler3.postDelayed(runnableCode3, 1000);
                    }
                };
                handler2.postDelayed(runnableCode2, 1500);
            }
        };
        // Run the above code block on the main thread after 2 seconds
        handler1.postDelayed(runnableCode1, 1500);







    }

    public void showQuatity(){
        Intent intent = new Intent(this, TankVolume.class);
        startActivity(intent);
    }
}
