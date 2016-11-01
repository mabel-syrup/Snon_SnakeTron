package com.surber.m.snonsnaketron;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    ImageView gameScreen;

    Byte[][] map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        gameScreen = (ImageView) findViewById(R.id.game_screen);

    }




}
