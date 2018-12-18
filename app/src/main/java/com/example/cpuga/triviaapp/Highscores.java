package com.example.cpuga.triviaapp;

import android.util.Log;

import java.io.Serializable;

public class Highscores implements Serializable {
    private int score;
    private int correct;
    private int incorrect;

    public Highscores () {
        score = 0;
        correct = 0;
        incorrect = 0;
    }

    // Getters and setters
    public void updateCorrect() {
        correct += 1;
    }

    public int getCorrect() {
        return correct;
    }

    public void updateIncorrect() {
        incorrect += 1;
    }

    public int getIncorrect() {
        return incorrect;
    }

    public void updateScore () {
        score += 1;
    }

    public int getHighscore () {
        return score;
    }
}
