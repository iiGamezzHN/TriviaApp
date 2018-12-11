package com.example.cpuga.triviaapp;

import android.util.Log;

import java.io.Serializable;

public class Highscores implements Serializable {
    private int score;

    public Highscores () {
        score = 0;
    }

    public void updateScore (int i) {
        if (i >= 9) {
            Log.d("lala", "lala");
        } else {
            score += 1;
        }

    }

    public int getHighscore () {
        return score;
    }
}
