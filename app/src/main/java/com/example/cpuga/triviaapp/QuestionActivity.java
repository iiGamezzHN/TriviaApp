package com.example.cpuga.triviaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity implements QuestionsRequest.Callback {
    TextView questionCategory;
    TextView questionDifficulty;
    TextView questionQuestion;
    TextView questionCounter;
    TextView tvHighscore;
    Trivia trivia;
    Highscores highscores;
    ArrayList<Question> questionsArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        highscores = new Highscores();

        questionCategory = findViewById(R.id.questionCategory);
        questionDifficulty = findViewById(R.id.questionDifficulty);
        questionQuestion = findViewById(R.id.questionQuestion);
        questionCounter = findViewById(R.id.questionCounter);

        QuestionsRequest request = new QuestionsRequest(this);
        request.getQuestion(this);
        Toast.makeText(this, "Started", Toast.LENGTH_SHORT).show();
    }

    public void gotQuestion(final ArrayList<Question> questions) {
        trivia = new Trivia(questions);
        questionsArray = questions;
        String test = questions.get(0).getCategory();
        Toast.makeText(this, test, Toast.LENGTH_SHORT).show();

        questionCategory.setText(questionsArray.get(0).getCategory());
        String diff = "Difficulty: "+questionsArray.get(0).getDifficulty();
        questionDifficulty.setText(diff);
        questionQuestion.setText(questionsArray.get(0).getQuestion());
        questionCounter.setText(String.valueOf(trivia.nextQuestion()+1));

    }

    public void nextQuestion(View view) {
        tvHighscore = findViewById(R.id.questionHighscore);
        trivia.updateAnswered();
        if (trivia.allAnswered()) {
            Toast.makeText(this, "All Answered", Toast.LENGTH_SHORT).show();
            String count = String.valueOf(trivia.nextQuestion()+1) + "/10";
            questionCounter.setText(count);
        }

        int nr = trivia.nextQuestion();

        highscores.updateScore(nr);


        Log.d("nummers", "index is " +nr);
        questionCategory.setText(questionsArray.get(nr).getCategory());
        String diff = "Difficulty: "+questionsArray.get(nr).getDifficulty();
        questionDifficulty.setText(diff);
        questionQuestion.setText(questionsArray.get(nr).getQuestion());

        String count = String.valueOf(trivia.nextQuestion()+1) + "/10";
        questionCounter.setText(count);

        String highscore = "Your score is: " + highscores.getHighscore();
        tvHighscore.setText(highscore);

        if (trivia.nextQuestion() == 9) {
            String highscore2 = "Your score is: " + highscores.getHighscore();
            tvHighscore.setText(highscore2);
        }

    }

    public void gotQuestionError(String message) {
        ;
    }
}
