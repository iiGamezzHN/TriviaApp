package com.example.cpuga.triviaapp;

import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;

public class Trivia implements Serializable {
    public int size;
    private int nr_answered = 0;
    private ArrayList qArray;

    public Trivia (ArrayList arrayList) {
        qArray = arrayList;
        size = qArray.size();
    }

    // Returns true if everything is answered
    public boolean allAnswered() {
        return nr_answered >= qArray.size()-1;
    }

    // Return nr of answered questions
    public Integer nextQuestion() {
        if (allAnswered()) {
            return 9;
        } else {
            return nr_answered;
        }
    }

    // Updates nr_answered questions
    public void updateAnswered () {
        nr_answered += 1;
    }
}
