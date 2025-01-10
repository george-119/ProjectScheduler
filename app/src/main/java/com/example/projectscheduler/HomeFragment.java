package com.example.projectscheduler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.core.content.ContextCompat;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment implements View.OnClickListener {
    private static final int STORAGE_PERMISSION_CODE = 101;
    CardView btn1,btn2,btn3;


//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, STORAGE_PERMISSION_CODE);
        btn1=(CardView) view.findViewById(R.id.file);
//        btn2=(Button)view.findViewById(R.id.pro);
        btn3=(CardView) view.findViewById(R.id.member);
        btn1.setOnClickListener(this);
//        btn2.setOnClickListener(this);
        btn3.setOnClickListener(this);
        return view;

    }
    private AlphaAnimation buttonClick= new AlphaAnimation(1F,0.6F);
    @Override
    public void onClick(View v) {
        v.startAnimation(buttonClick);
                switch (v.getId()){
                    case R.id.file:
                        Intent set = new Intent(getActivity().getApplicationContext(),ViewFile.class);
                        startActivity(set);
                        break;
//                    case R.id.pro:
//                        Intent Intent = new Intent(getActivity().getApplicationContext(),Progress.class);
//                        startActivity(Intent);
//                        break;
                    case R.id.member:
                        Intent inten = new Intent(getActivity().getApplicationContext(),ViewMember.class);
                        startActivity(inten);
                }
            }

    private void checkPermission(String permission, int requestCode) {
        if (ContextCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{permission}, requestCode);
        } else {
//            Toast.makeText(getActivity(), "permission already granted", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == STORAGE_PERMISSION_CODE){
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getActivity(),"Storage Permission Granted",Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(getActivity(),"Storage Permission Denied",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
