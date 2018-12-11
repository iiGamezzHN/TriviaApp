package com.example.cpuga.triviaapp;

import android.util.Log;
import java.io.Serializable;
import java.util.ArrayList;

public class Trivia implements Serializable {
    private int nr_answered = 0;
    private ArrayList qArray;

    public Trivia (ArrayList arrayList) {
        qArray = arrayList;
    }

    public boolean allAnswered() {
        Log.d("asdfff", nr_answered + " " + qArray.size());
        return nr_answered >= qArray.size()-1;
    }

    public Integer nextQuestion() {
        if (allAnswered()) {
            return 9;
        } else {
            return nr_answered;
        }
    }

    public void updateAnswered () {
        nr_answered += 1;
    }
}
