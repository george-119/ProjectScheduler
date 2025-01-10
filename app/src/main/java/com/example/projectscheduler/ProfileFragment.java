package com.example.projectscheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    TextView t1, t2, t3, t4, t5, t6, t7, t8;
    private ProgressBar spinner;

    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public ProfileFragment() {
        // Required empty public constructor
    }

    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        t1 = (TextView) view.findViewById(R.id.textView22);
        t2 = (TextView) view.findViewById(R.id.textView23);
        t3 = (TextView) view.findViewById(R.id.textView24);
        t4 = (TextView) view.findViewById(R.id.textView25);
        t5 = (TextView) view.findViewById(R.id.textView26);
        t6 = (TextView) view.findViewById(R.id.textView27);
        t7 = (TextView) view.findViewById(R.id.textView28);
//        t8 = (TextView) view.findViewById(R.id.textView32);
//        t8.setOnClickListener(this);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar1);
        spinner.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000/and_profile";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getActivity(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                String name = jsonObj.getString("name");
                                String count = jsonObj.getString("count");
                                String mail = jsonObj.getString("mail");
                                String lang = jsonObj.getString("lang");
                                String batch = jsonObj.getString("batch");
                                String state = jsonObj.getString("state");
                                String date = jsonObj.getString("date");


                                t1.setText(name);
                                t2.setText(count);
                                t3.setText(mail);
                                t4.setText(lang);
                                t5.setText(batch);
                                t6.setText(state);
                                t7.setText(date);
                                spinner.setVisibility(View.INVISIBLE);
                            } else{
                                Toast.makeText(getActivity().getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getActivity().getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getActivity().getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();
                params.put("lid", sh.getString("lid", ""));
                return params;
            }
        };
        int MY_SOCKET_TIMEOUT_MS = 100000;
        postRequest.setRetryPolicy(new DefaultRetryPolicy(MY_SOCKET_TIMEOUT_MS, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
            return view;
        }
//    private AlphaAnimation buttonClick= new AlphaAnimation(1F,0.6F);
//    @Override
//    public void onClick(View v){
//        v.startAnimation(buttonClick);
//        switch(v.getId()){
//            case R.id.textView32:
//                startActivity(new Intent(getActivity().getApplicationContext(), ViewMember.class));
//                break;
//        }
//    }
    }
