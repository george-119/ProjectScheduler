package com.example.projectscheduler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;

//public class IpSet extends AppCompatActivity implements View.OnClickListener {
public class IpSet extends AppCompatActivity implements View.OnClickListener {
    SharedPreferences sp;
    EditText e1;
    Button b1,b2;
    TextInputLayout iperror;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_ip);
        e1 = (EditText) findViewById(R.id.editText);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button) findViewById(R.id.clear);
        iperror = (TextInputLayout) findViewById(R.id.iperror);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip = sh.getString("ip", "");


        sp=getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int j=sp.getInt("key", 0);

        if (j >0) {
            Intent activity = new Intent(getApplicationContext(), Login.class);
//            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            String ip = sh.getString("ip", "");
            e1.setText(ip);
            startActivity(activity);
        }
        e1.setText(ip);
    }

    private AlphaAnimation buttonClick = new AlphaAnimation(1F, 0.6F);

    @Override
    public void onClick(View view) {
        view.startAnimation(buttonClick);
        if (view == b2) {
            e1.setText("");
        } else if (view == b1) {
            if (e1.getText().toString().isEmpty()) {
                iperror.setError("Please enter IP address");
            } else {
                String ip = e1.getText().toString();
                if (Patterns.IP_ADDRESS.matcher(ip).matches()) {
                    iperror.setError("");
                    SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor ed = sh.edit();
                    ed.putString("ip", ip);
                    ed.commit();

                    Intent ii = new Intent(getApplicationContext(), Login.class);
                    startActivity(ii);
                } else {
                    iperror.setError("Enter valid IP address");
                }
            }
            }
        }
        boolean doubleBackToPressedOnce = false;
        @Override
        public void onBackPressed () {
            if (doubleBackToPressedOnce) {
                finishAffinity();
            }
            this.doubleBackToPressedOnce = true;
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackToPressedOnce = false;
                }
            }, 2000);
        }

        @Override
        public void onPointerCaptureChanged ( boolean hasCapture){

        }
}
