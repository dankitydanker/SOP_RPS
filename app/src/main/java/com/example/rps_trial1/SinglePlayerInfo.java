package com.example.rps_trial1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class SinglePlayerInfo extends AppCompatActivity {

    EditText et1PNumRounds;

    Button bu1PStart, bu1PQuit;

    String numRoundsStr;
    int numRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player_info);

        et1PNumRounds = findViewById(R.id.et1PNumRounds);
        bu1PStart = findViewById(R.id.bu1PStart);
        bu1PQuit = findViewById(R.id.bu1PQuit);

        numRoundsStr = null;
    }

    public void bu1PStart(View view) {

        numRoundsStr = et1PNumRounds.getText().toString();
        if (numRoundsStr.isEmpty()){
            Toast.makeText(this, "Enter Number of Rounds!", Toast.LENGTH_SHORT).show();
        } else {
            numRounds = parseInt(numRoundsStr);
            if (numRounds <= 0){

            } else {
                Intent player1Game = new Intent(this, SinglePlayer.class);
                player1Game.putExtra("TOTAL_ROUNDS", numRounds);
                et1PNumRounds.setText("");
                startActivity(player1Game);
            }
        }

    }

    public void bu1PQuit(View view) {
        et1PNumRounds.setText("");
        finish();
    }
}
