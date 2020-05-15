package com.example.rps_trial1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import static java.lang.Integer.parseInt;

public class PlayerInfo extends AppCompatActivity {

    Button buStart2Player, buBack;

    EditText etP1Name, etP2Name, etNumRounds;

    String p1Name, p2Name, numRoundsStr;
    int numRounds;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_info);

        p1Name = null;
        p2Name = null;
        numRoundsStr = null;

        buStart2Player = findViewById(R.id.buStart2Player);
        buBack = findViewById(R.id.buBack);
        etP1Name = findViewById(R.id.etP1Name);
        etP2Name = findViewById(R.id.etP2Name);
        etNumRounds = findViewById(R.id.etNumRounds);
    }

    public void buStart2Player(View v){
        p1Name = etP1Name.getText().toString();
        p2Name = etP2Name.getText().toString();
        numRoundsStr = etNumRounds.getText().toString();
        if ( p1Name.isEmpty() || p2Name.isEmpty() || numRoundsStr.isEmpty()){
            Toast.makeText(getApplicationContext(),"Enter all fields!",Toast.LENGTH_SHORT).show();
        } else {
            numRounds = parseInt(numRoundsStr);
            if (numRounds <=0 ){
                Toast.makeText(getApplicationContext(),"Enter valid rounds!",Toast.LENGTH_SHORT).show();
            } else {
                Intent player2Game = new Intent(getApplicationContext(),Double.class);
                player2Game.putExtra("P1_NAME", p1Name);
                player2Game.putExtra("P2_NAME", p2Name);
                player2Game.putExtra("NUM_ROUNDS", numRounds);
                etNumRounds.setText("");
                etP1Name.setText("");
                etP2Name.setText("");
                startActivity(player2Game);
                //Toast.makeText(getApplicationContext(),"Starting...",Toast.LENGTH_SHORT).show();


            }
        }
    }

    public void buQuit2Player(View v){
        etNumRounds.setText("");
        etP1Name.setText("");
        etP2Name.setText("");
        finish();
    }

}
