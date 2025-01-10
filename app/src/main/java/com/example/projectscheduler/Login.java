package com.example.projectscheduler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText e1, e2;
    Button b1,b2;
    TextInputLayout usererror,passerror;
    SharedPreferences sp;
    private ProgressBar spinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        e1 = (EditText) findViewById(R.id.editText2);
        e2 = (EditText) findViewById(R.id.editText3);
        b1 = (Button) findViewById(R.id.button);
        b2 = (Button)findViewById(R.id.clear);
        usererror = (TextInputLayout) findViewById(R.id.usererror);
        passerror = (TextInputLayout) findViewById(R.id.passerror);
        b1.setOnClickListener(this);
        b2.setOnClickListener(this);
        spinner = (ProgressBar) findViewById(R.id.progressBar);
        spinner.setVisibility(View.GONE);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String user = sh.getString("username", "");


        sp = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);
        int j = sp.getInt("key", 0);

        if (j > 0) {
            Intent activity = new Intent(getApplicationContext(), Home.class);
//            finish();
//            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//            String user = sh.getString("username", "");
            e1.setText(user);
            startActivity(activity);
        }
        e1.setText(user);
    }
    private AlphaAnimation buttonClick= new AlphaAnimation(1F,0.6F);
    @Override
    public void onClick(View view) {
        view.startAnimation(buttonClick);
        if(view==b2){
            e1.setText("");
            e2.setText("");
        }
        else if (view==b1){
            if (e1.getText().toString().isEmpty()) {
                usererror.setError("Enter Username");
            } else if (e2.getText().toString().isEmpty()) {
                usererror.setError("");
                passerror.setError("Enter Password");
            } else {
                passerror.setError("");
                spinner.setVisibility(View.VISIBLE);
                String user = e1.getText().toString();
                SharedPreferences shr = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                SharedPreferences.Editor ed = shr.edit();
                ed.putString("username", user);
                ed.commit();
                final String username = e1.getText().toString();
                final String password = e2.getText().toString();
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String ip = sh.getString("ip", "");
                String url = "http://" + ip + ":5000/and_login";
//            Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
//                            Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();
                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
//                                    Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                                        SharedPreferences.Editor editor = sp.edit();
                                        editor.putInt("key", 1);
                                        editor.apply();

                                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        SharedPreferences.Editor ed = sh.edit();
                                        ed.putString("lid", jsonObj.getString("lid"));
                                        ed.putString("batch", jsonObj.getString("batch"));
                                        ed.putString("project", jsonObj.getString("projectname"));
                                        ed.commit();

                                        Intent ii = new Intent(getApplicationContext(), Home.class);
//                                    e1.setText("");
                                        e2.setText("");
                                        spinner.setVisibility(View.INVISIBLE);
                                        startActivity(ii);
//                                    finish();

                                    } else {
                                        Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                                        spinner.setVisibility(View.INVISIBLE);

                                    }

                                } catch (Exception e) {
                                    Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                    spinner.setVisibility(View.INVISIBLE);

                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(getApplicationContext(), "eeeee__" + error.toString(), Toast.LENGTH_SHORT).show();
                                e1.setText("");
                                e2.setText("");
                                spinner.setVisibility(View.INVISIBLE);


                            }
                        }

                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("username", username);
                        params.put("password", password);
                        return params;
                    }
                };
                int MY_SOCKET_TIMEOUT_MS = 100000;
                postRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                requestQueue.add(postRequest);
            }
        }
    }
}
