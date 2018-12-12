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
//        String test = questions.get(0).getCategory();

        questionCategory.setText(questionsArray.get(0).getCategory());
        String diff = "Difficulty: "+questionsArray.get(0).getDifficulty();
        questionDifficulty.setText(diff);
        questionQuestion.setText(questionsArray.get(0).getQuestion());
        answer.setText(questionsArray.get(0).getCorrect());

        questionCounter.setText(count);

    }

    public void updateScore (View view, int nr) {
        tvHighscore = findViewById(R.id.questionHighscore);
        String answer = questionsArray.get(nr).getCorrect();

        switch(view.getId()) {
            case R.id.questionTrue:
                if (answer.equals("True")) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    highscores.updateScore(nr);
                } else {
                    Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.questionFalse:
                if (answer.equals("False")) {
                    Toast.makeText(this, "Correct!", Toast.LENGTH_SHORT).show();
                    highscores.updateScore(nr);
                } else {
                    Toast.makeText(this, "Incorrect!", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        String highscore = "Your score is: " + highscores.getHighscore();
        tvHighscore.setText(highscore);
    }

    public boolean nextQuestion(View view) {
        if (trivia.nextQuestion() == 0) {
            firstAnswer(view, trivia.nextQuestion());
            return true;
        }
        int nr = trivia.nextQuestion();

        Log.d("qnr", String.valueOf(nr));

        updateScore(view, nr);

//        if (trivia.allAnswered()) {
//            Toast.makeText(this, "All Answered", Toast.LENGTH_SHORT).show();
//            String count = String.valueOf(trivia.nextQuestion()+1) + "/10";
//            questionCounter.setText(count);
//        }

        questionCategory.setText(questionsArray.get(nr).getCategory());
        String diff = "Difficulty: "+questionsArray.get(nr).getDifficulty();
        questionDifficulty.setText(diff);
        questionQuestion.setText(questionsArray.get(nr).getQuestion());
        String count = String.valueOf(trivia.nextQuestion()+1) + "/10";
        questionCounter.setText(count);
        answer.setText(questionsArray.get(nr).getCorrect());
        trivia.updateAnswered(); // +1 to nr of answered questions, now 1 too high
        return true;
    }

    public void firstAnswer(View view, int nr) {
        updateScore(view, trivia.nextQuestion());
        trivia.updateAnswered(); // updates nr of answered q, but breaks nextQ

        int new_nr = trivia.nextQuestion();
        Log.d("qnr", String.valueOf(new_nr));

        questionCategory.setText(questionsArray.get(new_nr).getCategory());
        String diff = "Difficulty: "+questionsArray.get(new_nr).getDifficulty();

        questionDifficulty.setText(diff);

        questionQuestion.setText(questionsArray.get(new_nr).getQuestion());

        String count = String.valueOf(trivia.nextQuestion()+1) + "/10";
        questionCounter.setText(count);

        answer.setText(questionsArray.get(new_nr).getCorrect());
    }

    public void gotQuestionError(String message) {
        int nr = trivia.nextQuestion();
        String answer = questionsArray.get(nr).getCorrect();

        if (trivia.allAnswered()) {

            Toast.makeText(this, answer, Toast.LENGTH_SHORT).show();
            String count = String.valueOf(trivia.nextQuestion()+1) + sizing;
            questionCounter.setText(count);
        } else {

            tvHighscore = findViewById(R.id.questionHighscore);
            trivia.updateAnswered();

            questionCategory.setText(questionsArray.get(nr).getCategory());
            String diff = "Difficulty: " + questionsArray.get(nr).getDifficulty();
            questionDifficulty.setText(diff);
            questionQuestion.setText(questionsArray.get(nr).getQuestion());

            String count = String.valueOf(trivia.nextQuestion() + 1) + sizing;
            questionCounter.setText(count);

            String highscore = "Your score is: " + highscores.getHighscore();
            tvHighscore.setText(highscore);

            if (trivia.nextQuestion() == 9) {
                String highscore2 = "Your score is: " + highscores.getHighscore();
                tvHighscore.setText(highscore2);
            }
        }
    }
}
