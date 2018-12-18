package com.example.cpuga.triviaapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity implements Response.Listener<String>,
        Response.ErrorListener, QuestionsRequest.Callback {
    // Initialize variables
    TextView questionCategory;
    TextView questionDifficulty;
    TextView questionQuestion;
    TextView questionCounter;
    TextView tvHighscore;
    TextView answer;
    Trivia trivia;
    Highscores highscores;
    ArrayList<Question> questionsArray;
    String sizing;
    int score;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        highscores = new Highscores(); // Create new highscore object

        questionCategory = findViewById(R.id.questionCategory);
        questionDifficulty = findViewById(R.id.questionDifficulty);
        questionQuestion = findViewById(R.id.questionQuestion);
        questionCounter = findViewById(R.id.questionCounter);

        QuestionsRequest request = new QuestionsRequest(this); // Make request for the questions
        request.getQuestion(this);
    }

    // Process the first question
    public void gotQuestion(final ArrayList<Question> questions) {
        trivia = new Trivia(questions);
        sizing = "1/"+String.valueOf(trivia.size);
        String count = sizing;
        questionsArray = questions;

        questionCategory.setText(questionsArray.get(0).getCategory());
        String diff = "Difficulty: "+questionsArray.get(0).getDifficulty();
        questionDifficulty.setText(diff);
        questionQuestion.setText(Html.fromHtml(questionsArray.get(0).getQuestion(), Html.FROM_HTML_MODE_COMPACT));
        answer.setText(questionsArray.get(0).getCorrect());

        questionCounter.setText(count);

    }

    // Check if answer was correct and update score accordingly
    public void updateScore (View view, int nr) {
        tvHighscore = findViewById(R.id.questionHighscore);
        String answer = questionsArray.get(nr).getCorrect();

        switch (view.getId()) {
            case R.id.questionTrue:
                if (answer.equals("True")) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    highscores.updateScore();
                    highscores.updateCorrect();
                } else {
                    Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    highscores.updateIncorrect();
                }
                break;
            case R.id.questionFalse:
                if (answer.equals("False")) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    highscores.updateScore();
                    highscores.updateCorrect();
                } else {
                    Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
                    highscores.updateIncorrect();
                }
                break;
        }

        String highscore = "Your score is: " + highscores.getHighscore();
        tvHighscore.setText(highscore);

        // Go to new activity when 10 questions are answered
        if (highscores.getCorrect() + highscores.getIncorrect() == 10) {
            Log.d("answered", "10");
            score = highscores.getHighscore();
            String winMessage = "You scored " + score + " points!";
            Toast.makeText(this, winMessage, Toast.LENGTH_SHORT).show();
            post();
        }
    }

    // Updates the score and shows the next question
    public boolean nextQuestion(View view) {
        int nr = trivia.nextQuestion();

        Log.d("qnr", String.valueOf(nr));

        updateScore(view, nr);
        trivia.updateAnswered(); // +1 to nr of answered questions
        nr = trivia.nextQuestion();

        questionCategory.setText(questionsArray.get(nr).getCategory());
        String diff = "Difficulty: " + questionsArray.get(nr).getDifficulty();
        questionDifficulty.setText(diff);
        questionQuestion.setText(Html.fromHtml(questionsArray.get(nr).getQuestion(), Html.FROM_HTML_MODE_COMPACT));
        String count = String.valueOf(trivia.nextQuestion() + 1) + "/10";
        questionCounter.setText(count);
        answer.setText(questionsArray.get(nr).getCorrect());
        return true;
    }

    public void gotQuestionError(String message) {

    }

    // Post the score to the database when all questions are answered
    public void post() {
        String url = "http://ide50-davidarisz.cs50.io:8080/list";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        PostRequest request = new PostRequest(Request.Method.POST, url, this, this, score);
        queue.add(request);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}
