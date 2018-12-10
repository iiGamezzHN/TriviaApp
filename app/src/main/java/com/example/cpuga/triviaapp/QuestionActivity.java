package com.example.cpuga.triviaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity implements QuestionsRequest.Callback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        QuestionsRequest request = new QuestionsRequest(this);
        request.getQuestion(this);
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
    }

    public void gotQuestion(final ArrayList<Question> questions) {
        ;
    }

    public void gotQuestionError(String message) {
        ;
    }
}
