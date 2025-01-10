package com.example.projectscheduler;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Feedback extends AppCompatActivity implements View.OnClickListener {
    TextView fb,fb1,feedback1,feedback2;
    Button dialer,insta,whatsapp,text,gmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        fb=(TextView)findViewById(R.id.fb);
        fb1=(TextView)findViewById(R.id.fb1);
        feedback1=(TextView)findViewById(R.id.feedback1);
        feedback2=(TextView)findViewById(R.id.feedback2);
        dialer=(Button)findViewById(R.id.button9);
        dialer.setOnClickListener(this);
//        insta=(Button)findViewById(R.id.button2);
//        insta.setOnClickListener(this);
//        whatsapp=(Button)findViewById(R.id.button7);
//        whatsapp.setOnClickListener(this);
        text=(Button)findViewById(R.id.button8);
        text.setOnClickListener(this);
        gmail=(Button)findViewById(R.id.button6);
        gmail.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
       switch (v.getId()){
           case R.id.button9:
               Intent intent=new Intent(Intent.ACTION_DIAL);
               intent.setData(Uri.parse("tel:8943925696"));
               startActivity(intent);
               break;
           case R.id.button8:
               String number="8943925696";
               startActivity(new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",number,null)));
               break;
//           case R.id.button7:
//               PackageManager pm=getPackageManager();
//               try{
//                   Intent waintent=new Intent(Intent.ACTION_SEND);
//                   waintent.setType("text/plain");
//                   String text="Download Project Scheduler Now" +
//                           " http://play.google.com/store/apps/details?id=com.ansangha.drdriving";
//
//                   PackageInfo info=pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
//                   waintent.setPackage("com.whatsapp");
//                   waintent.putExtra(Intent.EXTRA_TEXT, text);
//                   startActivity(Intent.createChooser(waintent,"Share with"));
//               } catch (PackageManager.NameNotFoundException e) {
//                   Toast.makeText(this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
//               }
//               break;
//           case R.id.button2:
//
//                  Uri ur = Uri.parse("http://instagram.com/_u/__ge_.o__rge_");
//                  Intent insta = new Intent(Intent.ACTION_VIEW, ur);
//                  insta.setPackage("com.instagram.android");
//                  try{
//                      startActivity(insta);
//                  }catch (ActivityNotFoundException e) {
//                      startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/__ge_.o__rge_")));
//                  }
//                  break;
           case R.id.button6:

                  Intent intent1 = new Intent(Intent.ACTION_SENDTO);
                  intent1.setData(Uri.parse("mailto:Projectscheduler10@gmail.com"));
//                  intent1.putExtra(Intent.EXTRA_EMAIL,"georgedevasia119@gmail.com");
//                  intent1.putExtra(intent1.EXTRA_SUBJECT,"Response From User");
//                  intent1.putExtra(intent1.EXTRA_TEXT,"Hello I am -");
                  intent1.setPackage("com.google.android.gm");
                  if(intent1.resolveActivity(getPackageManager())!=null) {
                      startActivity(intent1);
                  }
                  break;
       }
    }
}