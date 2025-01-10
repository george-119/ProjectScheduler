package com.example.projectscheduler;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.ActionBar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.google.android.material.navigation.NavigationView;

public class Home extends AppCompatActivity {
    DrawerLayout dLayout;
    SharedPreferences sp;
    TextView mTextMessage,group,batch;
    View headerview;
    MeowBottomNavigation mbn;

    private final static int Home_iD = 1;
    private final static int Profile_iD = 2;
    private final static int Guide_iD = 3;
    private final static int Schedule_iD = 4;
    private final static int Progress_iD = 5;
//    private final static int Logout_iD = 6;

    private static final int NUM_PAGES = 5;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        setNavigationDrawer();

        final ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);


        mbn = (MeowBottomNavigation) findViewById(R.id.nav_view);
        mTextMessage = (TextView) findViewById(R.id.message);

        mbn.add(new MeowBottomNavigation.Model(1, R.drawable.ic_home_black_24dp));
        mbn.add(new MeowBottomNavigation.Model(2, R.drawable.ic_baseline_face_24));
        mbn.add(new MeowBottomNavigation.Model(3, R.drawable.ic_dashboard_black_24dp));
        mbn.add(new MeowBottomNavigation.Model(4, R.drawable.ic_baseline_schedule_24));
        mbn.add(new MeowBottomNavigation.Model(5, R.drawable.ic_baseline_signal_cellular_alt_24));
//        mbn.add(new MeowBottomNavigation.Model(6, R.drawable.ic_baseline_login_24));

        sp = getSharedPreferences("autoLogin", Context.MODE_PRIVATE);

        mbn.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {

            }
        });
        mbn.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {

            }
        });
        mbn.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                Fragment fragment = null;
                switch (item.getId()) {
                    case Home_iD:
                        fragment = new HomeFragment();
                        break;
                    case Profile_iD:
                        fragment = new ProfileFragment();
                        break;
                    case Guide_iD:
                        fragment = new GuideFragment();
                        break;
                    case Schedule_iD:
                        fragment = new ScheduleFragment();
                        break;
                    case Progress_iD:
                        fragment = new ProgressviewFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });
//        mbn.setCount(3, "3");
//        mbn.setCount(4, "2");
        mbn.show(1, true);
    }

    private void setNavigationDrawer(){
        dLayout=(DrawerLayout)findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.navigation); // initiate a Navigation View
        View headerView = navView.getHeaderView(0);
        group = (TextView) headerView.findViewById(R.id.group);
        batch = (TextView) headerView.findViewById(R.id.batch);

        SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        group.setText(sh.getString("project",""));
        batch.setText(sh.getString("batch",""));
// implement setNavigationItemSelectedListener event on NavigationView
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                switch (menuItem.getItemId()) {
//                    case R.id.nav_progress:
//                        Intent pro = new Intent(getApplicationContext(), Progress.class);
//                        startActivity(pro);
//                        dLayout.closeDrawers();
//                        return true;
                    case R.id.nav_members:
                        Intent mem = new Intent(getApplicationContext(), ViewMember.class);
                        startActivity(mem);
                        dLayout.closeDrawers();
                        return true;
                    case R.id.nav_help:
                        Intent vie = new Intent(getApplicationContext(), Help.class);
                        startActivity(vie);
                        dLayout.closeDrawers();
                        return true;
                }
//
                return false;
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.root_container, fragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                dLayout.openDrawer(GravityCompat.START);
                return true;
            case R.id.logout:
                new AlertDialog.Builder(this).setIcon(R.drawable.ic_baseline_warning_24)
                        .setTitle("Are you sure you want to logout?").setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();
//                                Intent intent = new Intent(getApplicationContext(),Login.class);
//                                intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                startActivity(intent);
                                SharedPreferences.Editor editor = sp.edit();
                                editor.putInt("key", 0);
                                editor.apply();
                            }
                        })
                        .setNegativeButton("No", null).show();
                return true;
            case R.id.settings:
                Intent set = new Intent(getApplicationContext(), Settings.class);
                startActivity(set);
                return true;
//            case R.id.rateus:
//                Intent inte = new Intent(android.content.Intent.ACTION_VIEW);
//                inte.setData(Uri.parse("http://play.google.com/store/apps/details?id=com.ansangha.drdriving"));
//                inte.setPackage("com.android.vending");
//                startActivity(inte);
//                return true;
            case R.id.share:
                PackageManager pm = getPackageManager();
                try {
                    Intent waintent = new Intent(Intent.ACTION_SEND);
                    waintent.setType("text/plain");
                    String text = "Download Project Scheduler Now" +
                            " http://play.google.com/store/apps/details?id=com.ansangha.drdriving";

                    PackageInfo info = pm.getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
                    waintent.setPackage("com.whatsapp");
                    waintent.putExtra(Intent.EXTRA_TEXT, text);
                    startActivity(Intent.createChooser(waintent, "Share with"));
                } catch (PackageManager.NameNotFoundException e) {
                    Toast.makeText(this, "Whatsapp not installed", Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    boolean doubleBackToPressedOnce = false;
    @Override
    public void onBackPressed() {
        dLayout.closeDrawers();
        if (doubleBackToPressedOnce){
            finishAffinity();
        }
        this.doubleBackToPressedOnce = true;
        Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                doubleBackToPressedOnce = false;
            }
        },2000);
    }

}
