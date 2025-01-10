package com.example.projectscheduler;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectscheduler.R;
import com.squareup.picasso.Picasso;

public class Custom_view_project_schedule extends BaseAdapter {
    String[]pschid1,file1,batch1;
    private Context context;

    public Custom_view_project_schedule(Context appcontext,String[]pschid,String[]file,String[]batch)
    {
        this.context=appcontext;
        this.pschid1=pschid;
        this.file1=file;
        this.batch1=batch;

    }


    @Override
    public int getCount() {
        return batch1.length;
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
            gridView=inflator.inflate(R.layout.custom_view_project_schedule,null);

        }
        else
        {
            gridView=(View)view;

        }
        Button b=(Button) gridView.findViewById(R.id.button5);
        TextView tv1=(TextView)gridView.findViewById(R.id.textView39);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView38);

//        tv1.setTextColor(Color.GRAY);
//        tv2.setTextColor(Color.CYAN);


        tv1.setText(file1[i]);
        tv2.setText(batch1[i]);

//        String url_main = "";
        b.setTag(i);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos=(int)view.getTag();
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
                String hu = sh.getString("ip", "");
               String url_main = "http://" + hu + ":5000"+file1[pos];


//                open_file(url_main);


//                new FileDownloader


                Intent b= new Intent(Intent.ACTION_VIEW);
                Uri uri=Uri.parse(url_main);
//                b.setDataAndType(Uri.parse(url_main),"application/pdf");
                if (url_main.contains(".doc") || url_main.contains(".docx")) {
                    // Word document
                    b.setDataAndType(uri, "application/msword");
                } else if (url_main.contains(".pdf")) {
                    // PDF file
                    b.setDataAndType(uri, "application/pdf");
                } else if (url_main.contains(".ppt") || url_main.contains(".pptx")) {
                    // Powerpoint file
                    b.setDataAndType(uri, "application/vnd.ms-powerpoint");
                } else if (url_main.contains(".xls") || url_main.contains(".xlsx")) {
                    // Excel file
                    b.setDataAndType(uri, "application/vnd.ms-excel");
                } else if (url_main.contains(".zip") || url_main.contains(".rar")) {
                    // WAV audio file
                    b.setDataAndType(uri, "application/x-wav");
                } else if (url_main.contains(".rtf")) {
                    // RTF file
                    b.setDataAndType(uri, "application/rtf");
                } else if (url_main.contains(".wav") || url_main.contains(".mp3")) {
                    // WAV audio file
                    b.setDataAndType(uri, "audio/x-wav");
                } else if (url_main.contains(".gif")) {
                    // GIF file
                    b.setDataAndType(uri, "image/gif");
                } else if (url_main.contains(".jpg") || url_main.contains(".jpeg") || url_main.contains(".png")) {
                    // JPG file
                    b.setDataAndType(uri, "image/jpeg");
                } else if (url_main.contains(".txt")) {
                    // Text file
                    b.setDataAndType(uri, "text/plain");
                } else if (url_main.contains(".3gp") || url_main.contains(".mpg") || url_main.contains(".mpeg") || url_main.contains(".mpe") || url_main.contains(".mp4") || url_main.contains(".avi")) {
                    // Video files
                    b.setDataAndType(uri, "video/*");
                } else {
                    //if you want you can also define the intent type for any other file
                    //additionally use else clause below, to manage other unknown extensions
                    //in this case, Android will show all applications installed on the device
                    //so you can choose which application to use
                    b.setDataAndType(uri, "*/*");
                }

                b.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(b);
            }
        });



//        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
//        String ip=sh.getString("ip","");
//
//        String url="http://" + ip + ":5000"+img4[i];


//        Picasso.with(context).load(url). into(img4);

        return gridView;

    }
}