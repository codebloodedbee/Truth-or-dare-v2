package com.hizkeel.truthordare2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class GameOption extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_option);
    }

    public void pp(View v) {
        Intent intent = new Intent(this, GameQuestion.class);
        startActivity(intent);
    }
}