package com.example.iqrakhan.studentapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class TDashboardActivity extends AppCompatActivity {

    Button btnManageStudents;
    Button btnTakeAttendence;
    Button btnResults;
    Button btnNewsFeed;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tdashboard);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(TDashboardActivity.this);
        boolean isFirstTime = sharedPreferences.getBoolean("isFirstTime", true);
        boolean isUserLoggedIn = sharedPreferences.getBoolean("isUserLoggedIn", false);


        if(isFirstTime){
            startActivity(new Intent(TDashboardActivity.this, SplashActivity.class));
            finish();
            return;
        }

        if (!isUserLoggedIn) {
            startActivity(new Intent(TDashboardActivity.this, LoginActivity.class));
            finish();
        }

        btnManageStudents = (Button) findViewById(R.id.button_manageStudents);
        btnTakeAttendence = (Button) findViewById(R.id.button_takeAttendance);
        btnResults = (Button) findViewById(R.id.button_results);
        btnNewsFeed = (Button) findViewById(R.id.button_newsFeed);

        btnManageStudents.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(TDashboardActivity.this, "Managestudent", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(TDashboardActivity.this,ClassesActivity.class));
            }
        });
        btnTakeAttendence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnResults.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        btnNewsFeed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TDashboardActivity.this,NewsFeedActivity.class));
            }
        });
    }

}
