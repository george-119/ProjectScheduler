package com.example.projectscheduler;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProgressviewFragment extends Fragment {
    ListView lst;
    String[]file,percentage,date,progressid;
    String percen;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters

    public ProgressviewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProgressviewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProgressviewFragment newInstance(String param1, String param2) {
        ProgressviewFragment fragment = new ProgressviewFragment();
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
        View view = inflater.inflate(R.layout.fragment_progressview, container, false);
        lst=(ListView)view.findViewById(R.id.lst);
//        t1=(TextView) findViewById(R.id.textView34);
//        t2=(TextView) findViewById(R.id.textView41);
//        t3=(TextView) findViewById(R.id.textView44);

        final SwipeRefreshLayout pullToRefresh = view.findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Reload current fragment
                getActivity().getSupportFragmentManager().beginTransaction().replace(ProgressviewFragment.this.getId(), new ProgressviewFragment()).commit();
                pullToRefresh.setRefreshing(false);
            }
        });

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_view_progress";



        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //  Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js= jsonObj.getJSONArray("data");
                                progressid=new String[js.length()];
                                percentage=new String[js.length()];
                                date=new String[js.length()];
                                file=new String[js.length()];
//                                type=new String[js.length()];
//                                discription=new String[js.length()];
//                                image=new String[js.length()];
//                                status=new String[js.length()];
//
//                                JSONArray js1= jsonObj.getJSONArray("rating");
//                                rating=new String[js1.length()];

                                for(int i=0;i<js.length();i++)
                                {
                                    percen="%";
                                    JSONObject u=js.getJSONObject(i);
                                    progressid[i]=u.getString("progressid");
                                    percentage[i]=u.getString("percentage")+percen;
                                    date[i]=u.getString("date");
                                    file[i]=u.getString("file");
//                                    type[i]=u.getString("type");
//                                    discription[i]=u.getString("description");
//                                    image[i]=u.getString("image");
//                                    status[i]=u.getString("status");


                                }

//                                 ArrayAdapter<String> adpt=new ArrayAdapter<String>(getActivity().getApplicationContext(),android.R.layout.simple_list_item_1,name);

                                lst.setAdapter(new Custom_view_progress(getActivity().getApplicationContext(),percentage,file,date));
                                // l1.setAdapter(new Custom(getApplicationContext(),gamecode,name,type,discription,image,status));
                                if(lst == null || js.length() == 0){
//                                    Toast.makeText(getActivity().getApplicationContext(), "Database is Empty", Toast.LENGTH_LONG).show();
                                    new AlertDialog.Builder(getActivity()).setIcon(R.drawable.ic_baseline_warning_24)
                                            .setTitle("PROGRESS is empty").setCancelable(false)
                                            .setPositiveButton("ok", null).show();
                                }
                            }


                            // }
                            else {
                                Toast.makeText(getActivity().getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
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
                params.put("lid",sh.getString("lid",""));


//                params.put("mac",maclis);

                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    return view;
    }
}