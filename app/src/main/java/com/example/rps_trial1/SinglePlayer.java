package com.example.rps_trial1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

public class SinglePlayer extends AppCompatActivity {
    //
    TextView tvPScore, tvCScore;

    TextView tvTotalRoundsSing, tvCurrentRoundSing;

    TextView tvYourTurn;

    TextView tvPChose, tvCChose, tvResultSing;

    Button buRock, buPaper, buScissors;

    ConstraintLayout clBackground;

    Button buFinish;

    //
    String pName;

    int pScore, cScore;

    int totalRounds, currentRound;

    int pChoice, cChoice;

    boolean player;

    boolean pSelected, cSelected;
    boolean roundOver, gameOver;

    //int[] options = {1, 2, 3};
    int[] buOptions = {R.id.buRock, R.id.buPaper, R.id.buScissors};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_single_player);

        tvPScore = findViewById(R.id.tvPScore);
        tvCScore = findViewById(R.id.tvCScore);
        tvTotalRoundsSing = findViewById(R.id.tvTotalRoundsSing);
        tvCurrentRoundSing = findViewById(R.id.tvCurrentRoundSing);
        tvYourTurn = findViewById(R.id.tvYourTurn);

        tvPChose = findViewById(R.id.tvPChose);
        tvCChose = findViewById(R.id.tvCChose);
        tvResultSing = findViewById(R.id.tvResultSing);

        buRock = findViewById(R.id.buRock);
        buPaper = findViewById(R.id.buPaper);
        buScissors = findViewById(R.id.buScissors);

        buFinish = findViewById(R.id.buNewGame);

        clBackground = findViewById(R.id.clBackground);

        currentRound = 1;

        totalRounds = getIntent().getIntExtra("TOTAL_ROUNDS", 3);
        pName = getIntent().getStringExtra("P_NAME");

        pScore = 0;
        cScore = 0;

        tvTotalRoundsSing.setText("Total Rounds: " + totalRounds);
        tvPScore.setText("You: " + pScore);
        tvCScore.setText("CPU: " + cScore );
        tvCurrentRoundSing.setText("Round " + currentRound);

        tvYourTurn.setVisibility(View.VISIBLE);

        implementOnClickListener();
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("TOTAL_ROUNDS", totalRounds);
        outState.putInt("CURRENT_ROUND", currentRound);

        outState.putInt("P_SCORE", pScore);
        outState.putInt("C_SCORE", cScore);

        outState.putBoolean("IS_ROUND_OVER", roundOver);
        outState.putBoolean("IS_GAME_OVER", gameOver);

        outState.putInt("P_CHOICE", pChoice);
        outState.putInt("C_CHOICE", cChoice);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        currentRound = savedInstanceState.getInt("CURRENT_ROUND");

        pScore = savedInstanceState.getInt("P_SCORE");
        cScore = savedInstanceState.getInt("C_SCORE");

        gameOver = savedInstanceState.getBoolean("IS_GAME_OVER");
        roundOver = savedInstanceState.getBoolean("IS_ROUND_OVER");

        if(!gameOver)
        tvCurrentRoundSing.setText("Round "+currentRound);

        tvPScore.setText("You: "+pScore);
        tvCScore.setText("CPU: "+cScore);

        if (roundOver){
            pChoice = savedInstanceState.getInt("P_CHOICE");
            cChoice = savedInstanceState.getInt("C_CHOICE");
            checkResult(pChoice, cChoice);
        } else {
            showThings();
        }
    }

    public void implementOnClickListener(){
        for (final int buId : buOptions) {
            ((Button) findViewById(buId)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    pChoice = view.getId();
                    Random random = new Random();
                    int randChoiceInd = random.nextInt(buOptions.length);
                    cChoice = buOptions[randChoiceInd];


                    checkResult(pChoice, cChoice);
                }
            });
        }
    }

    public void checkResult(int pChoice, int cChoice){
        hideThings();
        showResults();

        Log.d(" check ", " Player: "+pChoice+", CPU: "+cChoice);

        switch (pChoice) {
            case R.id.buRock:// p1 rock
                switch (cChoice) {
                    case R.id.buRock: // draw
                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);

                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);

                        tvResultSing.setText("It's a draw!");
                        break;
                    case R.id.buPaper: // p2 wins
                        if (!roundOver)
                        cScore++;

                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);
                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);

                        tvResultSing.setText("CPU wins!");
                        break;
                    case R.id.buScissors: // p1 wins
                        if (!roundOver)
                        pScore++;

                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);
                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);

                        tvResultSing.setText("You win!");
                        break;
                }
                break;
            case R.id.buPaper: // p1 paper
                switch (cChoice) {
                    case R.id.buRock: // p1 wins
                        if (!roundOver)
                        pScore++;

                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);
                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);

                        tvResultSing.setText("You win!");
                        break;
                    case R.id.buPaper: // draw
                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);
                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);

                        tvResultSing.setText("It's a draw!");
                        break;
                    case R.id.buScissors: // p2 wins
                        if (!roundOver)
                        cScore++;

                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);
                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);

                        tvResultSing.setText("CPU wins!");
                        break;
                }
                break;
            case R.id.buScissors: // p1 scissor
                switch (cChoice) {
                    case R.id.buRock: // p2 wins
                        if (!roundOver)
                        cScore++;

                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);
                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_rock);

                        tvResultSing.setText("CPU wins!");
                        break;
                    case R.id.buPaper: // p1 wins
                        if (!roundOver)
                        pScore++;

                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);
                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_paper);

                        tvResultSing.setText("You win!");
                        break;
                    case R.id.buScissors: // draw
                        tvPChose.setText("You chose");
                        tvPChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);
                        tvCChose.setText("CPU chose");
                        tvCChose.setCompoundDrawablesWithIntrinsicBounds(0, 0, 0, R.drawable.ic_scissors);

                        tvResultSing.setText("It's a draw!");
                        break;
                }
                break;
        }

        tvPScore.setText("You: " + pScore);
        tvCScore.setText("CPU: " + cScore);

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
                    tvCurrentRoundSing.setText("Round " + currentRound);
                    roundOver = false;
                }
            }, 4000);

        }

    }

    public void gameOver(){
        hideThings();
        if (pScore > cScore) {
            Toast.makeText(this,  "You win!", Toast.LENGTH_SHORT).show();
            clBackground.setBackgroundColor(getResources().getColor(R.color.pWins));
            tvResultSing.setText("Game Over!\nYou win!");
        } else if (pScore == cScore) {
            Toast.makeText(this, "It's a draw!", Toast.LENGTH_SHORT).show();
            tvResultSing.setText("Game Over!\nIt's a draw!");
        } else {
            Toast.makeText(this, "You Lose :(", Toast.LENGTH_SHORT).show();
            clBackground.setBackgroundColor(getResources().getColor(R.color.pLoses));
            tvResultSing.setText("Game Over!\nYou Lose :(");
        }

        buFinish.setVisibility(View.VISIBLE);
        buFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    public void showThings() {
        buRock.setVisibility(View.VISIBLE);
        buPaper.setVisibility(View.VISIBLE);
        buScissors.setVisibility(View.VISIBLE);

        tvYourTurn.setVisibility(View.VISIBLE);
    }

    public void hideThings() {
        buRock.setVisibility(View.INVISIBLE);
        buPaper.setVisibility(View.INVISIBLE);
        buScissors.setVisibility(View.INVISIBLE);

        tvYourTurn.setVisibility(View.INVISIBLE);
    }

    public void showResults() {
        tvPChose.setVisibility(View.VISIBLE);
        tvCChose.setVisibility(View.VISIBLE);

        tvResultSing.setVisibility(View.VISIBLE);
    }

    public void hideResults() {
        tvPChose.setVisibility(View.INVISIBLE);
        tvCChose.setVisibility(View.INVISIBLE);

        tvResultSing.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        if ((currentRound - 1) != totalRounds){
            final AlertDialog.Builder adQuit = new AlertDialog.Builder(this);
            adQuit.setTitle("Quit?")
                    .setMessage("Are you sure you want to quit?")
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
}
