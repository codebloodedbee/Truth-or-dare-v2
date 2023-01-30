package com.hizkeel.truthordare2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VibeInfo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibe_info);
    }

    public void btnAddPlayers(View v) {
        Intent intent = new Intent(this, Players.class);
        startActivity(intent);
    }
}