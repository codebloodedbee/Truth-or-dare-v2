package com.hizkeel.truthordare2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Launch extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launch);




    }

    public void btnRegister(View v) {
        Intent intent = new Intent(this,Register.class);
        startActivity(intent);
    }
    public void btnLogin(View v) {
        Intent intent = new Intent(this, Login.class);
        startActivity(intent);
    }

}