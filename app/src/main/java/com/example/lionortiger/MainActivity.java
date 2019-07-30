package com.example.lionortiger;

import androidx.appcompat.app.AppCompatActivity;
import androidx.gridlayout.widget.GridLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    enum Player {
        ONE, TWO, NONE
    }

    Player currPlayer = Player.ONE;
    boolean gameOver = false;

    GridLayout gridLayout;

    Player[] playerChoices = new Player[9];
    int[][] winnerChoiceItems = {{0, 1, 2}, {0, 3, 6}, {0, 4, 8}, {3, 4, 5}, {6, 7, 8}, {1, 4, 7}, {2, 5, 8}, {2, 4, 6}};

    Button btnReset;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnReset = findViewById(R.id.btnReset);
        gridLayout = findViewById(R.id.gridLayout);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetGame();
            }
        });

        for (int i = 0; i < playerChoices.length; i++) {
            playerChoices[i] = Player.NONE;
        }
    }

    public void imageViewIsTapped(View imageView) {

        ImageView tappedImageView = (ImageView) imageView;

        int tag = Integer.parseInt(tappedImageView.getTag().toString());

        if (playerChoices[tag] == Player.NONE && gameOver == false) {
            tappedImageView.setTranslationX(-2000);
            playerChoices[tag] = currPlayer;
            count++;

            if (currPlayer == Player.ONE) {
                tappedImageView.setImageResource(R.drawable.lion);
                currPlayer = Player.TWO;
            } else {
                tappedImageView.setImageResource(R.drawable.tiger);
                currPlayer = Player.ONE;
            }

            for (int[] winnerBlocks : winnerChoiceItems) {
                if (playerChoices[winnerBlocks[0]] == playerChoices[winnerBlocks[1]]
                        && playerChoices[winnerBlocks[1]] == playerChoices[winnerBlocks[2]]
                        && playerChoices[winnerBlocks[0]] != Player.NONE) {
                    String winner = "";
                    btnReset.setVisibility(View.VISIBLE);
                    gameOver = true;
                    if (currPlayer == Player.ONE) {
                        winner = " Tiger Won";
                    } else {
                        winner = " Lion Won";
                    }

                    Toast.makeText(this, winner, Toast.LENGTH_LONG).show();
                }
            }

            tappedImageView.animate().translationXBy(2000).alpha(1).rotation(3600).setDuration(1000);
            if(count == gridLayout.getChildCount() && gameOver == false) {
                btnReset.setVisibility(View.VISIBLE);
                Toast.makeText(this, "Game Drawn", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void resetGame() {


        for (int index = 0; index < gridLayout.getChildCount(); index++) {
            ImageView resetImage = (ImageView) gridLayout.getChildAt(index);
            resetImage.setImageDrawable(null);
            resetImage.setAlpha(0.2f);
        }

        for (int i = 0; i < playerChoices.length; i++) {
            playerChoices[i] = Player.NONE;
        }
        currPlayer = Player.ONE;
        gameOver = false;
        btnReset.setVisibility(View.GONE);
        count = 0;

    }
}
