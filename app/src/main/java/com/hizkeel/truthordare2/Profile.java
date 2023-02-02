package com.hizkeel.truthordare2;

import static com.hizkeel.truthordare2.Login.sp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Profile extends AppCompatActivity {

    TextView pName, pLocation, pEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        pName =findViewById(R.id.pName);
        pEmail = findViewById(R.id.pEmail);
        pLocation = findViewById(R.id.pLocation);

        UserData ud = new UserData(getApplicationContext());


        pName.setText(ud.getFirstname()+" "+ ud.getLastname());
        pLocation.setText(ud.getState()+", "+ ud.getCountry());
        pEmail.setText(ud.getEmail());




        LinearLayout ll_about = (LinearLayout) findViewById(R.id.tvAboutUs);
        ll_about.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), AboutApp.class);
                startActivity(intent);
            }
        });

        LinearLayout ll_clear_history = (LinearLayout)findViewById(R.id.tvClearHistory);
        ll_clear_history.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), AboutApp.class);
                startActivity(intent);
            }
        });

        LinearLayout ll_help = (LinearLayout) findViewById(R.id.tvHelp);
        ll_help.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(getApplicationContext(), AboutApp.class);
                startActivity(intent);
            }
        });

        LinearLayout ll_rate_us = (LinearLayout) findViewById(R.id.tvRateUs);
        ll_rate_us.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(intent);

            }
        });

        LinearLayout ll_logout = (LinearLayout) findViewById(R.id.tvLogout);
        ll_logout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                sp.edit().remove("Semail").commit();
                sp.edit().remove("Spassword").commit();
                sp.edit().remove("logged").commit();


                Intent intent = new Intent(getApplicationContext(), SplashScreen.class);
                startActivity(intent);
            }
        });

        LinearLayout ll_privacy_policy = (LinearLayout) findViewById(R.id.tvPrivacyPolicy);
        ll_privacy_policy.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                Intent intent = new Intent(getApplicationContext(), PrivacyPolicy.class);
                startActivity(intent);

            }
        });
    }
}