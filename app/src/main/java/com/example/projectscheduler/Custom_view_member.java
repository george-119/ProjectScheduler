package com.example.projectscheduler;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

public class Custom_view_member extends BaseAdapter {
    String[]img1,name1,email1,phone1;
    private Context context;

    public Custom_view_member(Context appcontext,String[]img,String[]name,String[]email,String[]phone)
    {
        this.context=appcontext;
        this.img1=img;
        this.name1=name;
        this.email1=email;
        this.phone1=phone;

    }


    @Override
    public int getCount() {
        return name1.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.custom_view_member,null);

        }
        else
        {
            gridView=(View)view;

        }
        ImageView im=(ImageView) gridView.findViewById(R.id.imageView3);
        TextView tv1=(TextView)gridView.findViewById(R.id.textView21);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView30);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView31);

//        tv1.setTextColor(Color.CYAN);
//        tv2.setTextColor(Color.GRAY);
//        tv3.setTextColor(Color.GRAY);


        tv1.setText(name1[i]);
        tv2.setText(email1[i]);
        tv3.setText(phone1[i]);



        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");

        String url="http://" + ip + ":5000"+img1[i];



        Picasso.with(context).load(url). into(im);

        return gridView;

    }
}
