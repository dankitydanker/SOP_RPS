package com.example.rps_trial1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class MainActivity extends AppCompatActivity {

    Button bu2Player, bu1Player;

    ImageView ivMainMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bu2Player = findViewById(R.id.bu2Player);
        bu1Player = findViewById(R.id.bu1Player);

        ivMainMenu = findViewById(R.id.ivMainMenu);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                AnimationDrawable rps = (AnimationDrawable) ivMainMenu.getDrawable();
                rps.start();
            }
        },500);

    }


    public void bu2Player(View view) {
        Intent p2Info = new Intent(this, PlayerInfo.class);
        startActivity(p2Info);
    }


    public void bu1Player(View view) {
        Intent p1Info = new Intent(this, SinglePlayerInfo.class);
        startActivity(p1Info);
    }

    public void buQuitGame(View view) {
        finish();
    }
}
