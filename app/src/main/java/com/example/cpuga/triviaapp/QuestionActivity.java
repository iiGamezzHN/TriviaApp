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
    TextView answer;
    Trivia trivia;
    Highscores highscores;
    ArrayList<Question> questionsArray;
    String sizing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        highscores = new Highscores();

        questionCategory = findViewById(R.id.questionCategory);
        questionDifficulty = findViewById(R.id.questionDifficulty);
        questionQuestion = findViewById(R.id.questionQuestion);
        questionCounter = findViewById(R.id.questionCounter);
        answer = findViewById(R.id.answer);

        QuestionsRequest request = new QuestionsRequest(this);
        request.getQuestion(this);
    }

    public void gotQuestion(final ArrayList<Question> questions) {
        trivia = new Trivia(questions);
        sizing = "1/"+String.valueOf(trivia.size);
        String count = sizing;
        questionsArray = questions;

        questionCategory.setText(questionsArray.get(0).getCategory());
        String diff = "Difficulty: "+questionsArray.get(0).getDifficulty();
        questionDifficulty.setText(diff);
        questionQuestion.setText(questionsArray.get(0).getQuestion());
        answer.setText(questionsArray.get(0).getCorrect());

        questionCounter.setText(count);

    }

    public void updateScore (View view, int nr) {
        if (highscores.getCorrect() + highscores.getIncorrect() == 10) {
            Log.d("answered", "10");
            Intent intent = new Intent(this, HighscoresActivity.class);
            intent.putExtra("scoreTag", highscores.getHighscore());
            startActivity(intent);
        } else {
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
        }

        String highscore = "Your score is: " + highscores.getHighscore();
        tvHighscore.setText(highscore);
    }

    public boolean nextQuestion(View view) {
        int nr = trivia.nextQuestion();

        Log.d("qnr", String.valueOf(nr));

        updateScore(view, nr);
        trivia.updateAnswered(); // +1 to nr of answered questions, now 1 too high
        nr = trivia.nextQuestion();

        questionCategory.setText(questionsArray.get(nr).getCategory());
        String diff = "Difficulty: " + questionsArray.get(nr).getDifficulty();
        questionDifficulty.setText(diff);
        questionQuestion.setText(questionsArray.get(nr).getQuestion());
        String count = String.valueOf(trivia.nextQuestion() + 1) + "/10";
        questionCounter.setText(count);
        answer.setText(questionsArray.get(nr).getCorrect());
        return true;
    }

    public void gotQuestionError(String message) {

    }
}
