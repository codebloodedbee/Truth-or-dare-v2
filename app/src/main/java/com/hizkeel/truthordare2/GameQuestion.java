package com.hizkeel.truthordare2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameQuestion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_question);
    }

    public void done(View v) {
        Intent intent = new Intent(this, GameOption.class);
        startActivity(intent);
    }
}