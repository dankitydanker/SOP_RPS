package com.example.rps_trial1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Double extends AppCompatActivity {

    //
    TextView tvP1Score, tvP2Score;

    TextView tvP1Chose, tvP2Chose, tvResult;

    TextView tvCurrentRound, tvTotalRounds;

    TextView tvCurrentPlayer;

    Button buRock, buPaper, buScissors;

    Button buFinish;

    int[] buId = {R.id.buRock, R.id.buPaper, R.id.buScissor};

    //
    String P1Name, P2Name;

    boolean player1;

    int p1Choice, p2Choice;

    int p1Score, p2Score;

    int totalRounds, currentRound;

    boolean p1Selected, p2Selected;
    boolean roundOver, gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_double);

        tvP1Score = findViewById(R.id.tvP1Score);
        tvP2Score = findViewById(R.id.tvP2Score);

        tvP1Chose = findViewById(R.id.tvP1Chose);
        tvP2Chose = findViewById(R.id.tvP2Chose);
        tvResult = findViewById(R.id.tvResultSing);

        tvCurrentRound = findViewById(R.id.tvCurrentRoundSing);
        tvTotalRounds = findViewById(R.id.tvTotalRoundsSing);

        tvCurrentPlayer = findViewById(R.id.tvCurrentPlayer);

        buRock = findViewById(R.id.buRock);
        buPaper = findViewById(R.id.buPaper);
        buScissors = findViewById(R.id.buScissor);

        buFinish = findViewById(R.id.buFinish);

        P1Name = getIntent().getStringExtra("P1_NAME");
        P2Name = getIntent().getStringExtra("P2_NAME");
        totalRounds = getIntent().getIntExtra("NUM_ROUNDS", 3);

        currentRound = 1;

        p1Score = 0;
        p2Score = 0;

        tvTotalRounds.setText("Total Rounds: " + totalRounds);
        tvCurrentRound.setText("Round " + currentRound);

        tvP1Score.setText(P1Name + ": " + p1Score);
        tvP2Score.setText(P2Name + ": " + p2Score);

        tvCurrentPlayer.setText(P1Name + "'s turn");

        player1 = true;

        implementOnClickListener();


    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("TOTAL_ROUNDS", totalRounds);
        outState.putInt("CURRENT_ROUND", currentRound);

        outState.putInt("P1_SCORE", p1Score);
        outState.putInt("P2_SCORE", p2Score);

        outState.putBoolean("IS_GAME_OVER", gameOver);
        outState.putBoolean("IS_ROUND_OVER", roundOver);
        outState.putBoolean("P1_SELECTED", p1Selected);
        outState.putBoolean("P2_SELECTED", p2Selected);

        outState.putInt("P1_CHOICE", p1Choice);
        outState.putInt("P2_CHOICE", p2Choice);

//        if (roundOver) {
//            outState.putInt("P1_CHOICE", p1Choice);
//            outState.putInt("P2_CHOICE", p2Choice);
//        } else if (p1Selected && !p2Selected) {
//            outState.putInt("P1_CHOICE", p1Choice);
//        } else if {
//
//        }
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentRound = savedInstanceState.getInt("CURRENT_ROUND");

        p1Score = savedInstanceState.getInt("P1_SCORE");
        p2Score = savedInstanceState.getInt("P2_SCORE");

        gameOver = savedInstanceState.getBoolean("IS_GAME_OVER");
        roundOver = savedInstanceState.getBoolean("IS_ROUND_OVER");
        p1Selected = savedInstanceState.getBoolean("P1_SELECTED");
        p2Selected = savedInstanceState.getBoolean("P2_SELECTED");

        tvP1Score.setText(P1Name+": "+p1Score);
        tvP2Score.setText(P2Name+": "+p2Score);

        if (!gameOver)
        tvCurrentRound.setText("Round "+currentRound);

        if (roundOver){
            p1Choice = savedInstanceState.getInt("P1_CHOICE");
            p2Choice = savedInstanceState.getInt("P2_CHOICE");
            checkResult(p1Choice,p2Choice);
        } else if (p1Selected && !p2Selected) {
            showThings();
            p1Choice = savedInstanceState.getInt("P1_CHOICE");
            Log.d(" rotated ", " p1Choice: " + p1Choice);
            tvCurrentPlayer.setText(P1Name + "'s turn");
        } else if (!p1Selected && !p2Selected) {
            showThings();
            tvCurrentPlayer.setText(P1Name + "'s turn");
        } else if (gameOver){
            finish();
        }

    }

    public void implementOnClickListener() {
        for (final int buId : buId) {
            ((Button) findViewById(buId)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (player1) {
                        p1Choice = view.getId();
                        tvCurrentPlayer.setText(P2Name + "'s turn");
                        p1Selected = true;
                        player1 = !player1;
                    } else {
                        p2Choice = view.getId();
                        tvCurrentPlayer.setText(P1Name + "'s turn");
                        p2Selected = true;
                        player1 = !player1;
                        checkResult(p1Choice, p2Choice);
                    }
                }
            });
        }
    }

    public void checkResult(int p1Choice, int p2Choice) {
        hideThings();
        showResults();

        switch (p1Choice) {
            case R.id.buRock:// p1 rock
                switch (p2Choice) {
                    case R.id.buRock: // draw
                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);

                        tvP2Chose.setText(P2Name + " chose");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);

                        tvResult.setText("It's a draw!");
                        break;
                    case R.id.buPaper: // p2 wins
                        if(!roundOver)
                        p2Score++;

                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);
                        tvP2Chose.setText(P2Name + " chose");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);

                        tvResult.setText(P2Name + " wins!");
                        break;
                    case R.id.buScissor: // p1 wins
                        if(!roundOver)
                        p1Score++;

                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);
                        tvP2Chose.setText(P2Name + " chose");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);

                        tvResult.setText(P1Name + " wins!");
                        break;
                }
                break;
            case R.id.buPaper: // p1 paper
                switch (p2Choice) {
                    case R.id.buRock: // p1 wins
                        if(!roundOver)
                        p1Score++;

                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);
                        tvP2Chose.setText(P2Name + " chose");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);

                        tvResult.setText(P1Name+" wins!");
                        break;
                    case R.id.buPaper: // draw
                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);
                        tvP2Chose.setText(P2Name + " chose");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);

                        tvResult.setText("It's a draw!");
                        break;
                    case R.id.buScissor: // p2 wins
                        if(!roundOver)
                        p2Score++;

                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);
                        tvP2Chose.setText(P2Name + " chose");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);

                        tvResult.setText(P2Name + " wins!");
                        break;
                }
                break;
            case R.id.buScissor: // p1 scissor
                switch (p2Choice) {
                    case R.id.buRock: // p2 wins
                        if(!roundOver)
                        p2Score++;

                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);
                        tvP2Chose.setText(P2Name + " chose");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);

                        tvResult.setText(P2Name + " wins!");
                        break;
                    case R.id.buPaper: // p1 wins
                        if(!roundOver)
                        p1Score++;

                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);
                        tvP2Chose.setText(P2Name + " chose ");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);

                        tvResult.setText(P1Name + " wins!");
                        break;
                    case R.id.buScissor: // draw
                        tvP1Chose.setText(P1Name + " chose");
                        tvP1Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);
                        tvP2Chose.setText(P2Name + " chose");
                        tvP2Chose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);

                        tvResult.setText("It's a draw!");
                        break;
                }
                break;
        }

        tvP1Score.setText(P1Name + ": " + p1Score);
        tvP2Score.setText(P2Name + ": " + p2Score);

        p1Selected = false;
        p2Selected = false;
        roundOver = true;

        if ((currentRound) == totalRounds) {
            gameOver = true;
            gameOver();
        } else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    hideResults();
                    showThings();
                    currentRound++;
                    tvCurrentRound.setText("Round " + currentRound);
                    roundOver = false;
                }
            }, 4000);

        }
    }

    @Override
    public void onBackPressed() {
        if ((currentRound - 1) != totalRounds){
            final AlertDialog.Builder adQuit = new AlertDialog.Builder(this);
                    adQuit.setTitle("Quit?")
                    .setMessage("Are you sure you want oto quit?")
                    .setPositiveButton("Play", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.dismiss();
                        }
                    })
                    .setNegativeButton("Quit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            finish();
                        }
                    }).show();
    }else{
            super.onBackPressed();
        }
    }

    public void hideResults() {
        tvP1Chose.setVisibility(View.INVISIBLE);
        tvP2Chose.setVisibility(View.INVISIBLE);
        tvResult.setVisibility(View.INVISIBLE);
    }

    public void showResults() {
        tvP1Chose.setVisibility(View.VISIBLE);
        tvP2Chose.setVisibility(View.VISIBLE);

        tvResult.setVisibility(View.VISIBLE);
    }

    public void hideThings() {
        buRock.setVisibility(View.INVISIBLE);
        buPaper.setVisibility(View.INVISIBLE);
        buScissors.setVisibility(View.INVISIBLE);

        tvCurrentPlayer.setVisibility(View.INVISIBLE);
    }

    public void showThings() {
        buRock.setVisibility(View.VISIBLE);
        buPaper.setVisibility(View.VISIBLE);
        buScissors.setVisibility(View.VISIBLE);

        tvCurrentPlayer.setVisibility(View.VISIBLE);
    }

    public void gameOver() {
        hideThings();
        if (p1Score > p2Score) {
            Toast.makeText(this, P1Name + " wins!", Toast.LENGTH_SHORT).show();
            tvResult.setText("Game Over!" + "\n" + P1Name + " wins!");
        } else if (p1Score == p2Score) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
            tvResult.setText("Game Over!\nIt's a draw!");
        } else {
            Toast.makeText(this, P2Name + " wins!", Toast.LENGTH_SHORT).show();
            tvResult.setText("Game Over!" + "\n" + P2Name + " wins!");
        }

        buFinish.setVisibility(View.VISIBLE);
        buFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
