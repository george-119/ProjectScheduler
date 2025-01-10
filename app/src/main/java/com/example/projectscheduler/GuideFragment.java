package com.example.projectscheduler;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GuideFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GuideFragment extends Fragment {
    TextView t1, t2, t3, t4, t5, t6;
    ImageView img;
    ProgressBar spinner;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public GuideFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment GuideFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static GuideFragment newInstance(String param1, String param2) {
        GuideFragment fragment = new GuideFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_guide, container, false);
        img=(ImageView) view.findViewById(R.id.imageView);
        t1=(TextView) view.findViewById(R.id.textView2);
        t2=(TextView) view.findViewById(R.id.textView4);
        t3=(TextView) view.findViewById(R.id.textView6);
        t4=(TextView) view.findViewById(R.id.textView8);
        t5=(TextView) view.findViewById(R.id.textView10);
        t6=(TextView) view.findViewById(R.id.textView19);
        spinner = (ProgressBar) view.findViewById(R.id.progressBar2);
        spinner.setVisibility(View.GONE);
        spinner.setVisibility(View.VISIBLE);

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String ip = sh.getString("ip", "");
        String url = "http://" + ip + ":5000/and_internal_guide";
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getActivity().getApplicationContext(), response, Toast.LENGTH_LONG).show();
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                String name=jsonObj.getString("name");
                                String email=jsonObj.getString("email");
                                String phone=jsonObj.getString("phone");
                                String gender=jsonObj.getString("gender");
                                String place=jsonObj.getString("place");
                                String pin=jsonObj.getString("pin");
                                String img1=jsonObj.getString("image");


                                t1.setText(name);
                                t2.setText(email);
                                t3.setText(phone);
                                t4.setText(gender);
                                t5.setText(place);
                                t6.setText(pin);
                                spinner.setVisibility(View.INVISIBLE);


                                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
                                String ip=sh.getString("ip","");

                                String url="http://" + ip + ":5000"+img1;


                                Picasso.with(getActivity().getApplicationContext()).load(url). into(img);


                                if(name.isEmpty()&&email.isEmpty()&&phone.isEmpty()&&gender.isEmpty()&&place.isEmpty()&&pin.isEmpty()&&img==null){
                                    new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_baseline_warning_24)
                                            .setTitle("Internal Guide is Not Assigned").setCancelable(false)
                                            .setPositiveButton("ok", null).show();
                                }


                            } else {
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
}
