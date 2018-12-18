package com.example.cpuga.triviaapp;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class QuestionsRequest implements Response.Listener<JSONObject>, Response.ErrorListener{
    Context context;
    Callback activity;

    public interface Callback {
        void gotQuestion(ArrayList<Question> q);
        void gotQuestionError(String message);
    }

    public QuestionsRequest(Context con) {
        context = con;
    }

    public void getQuestion(Callback activity) {
        String url = "https://opentdb.com/api.php?amount=10&type=boolean";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);

        this.activity = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error.getMessage() == null)
            Toast.makeText(context, "Timeout error :(", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Timeout error :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONObject response) {
        ArrayList<Question> arrayList = new ArrayList();

        try {
            JSONArray questions = response.getJSONArray("results");
            Log.d("arraylen", "");
            Log.d("arraylen", "");
            Log.d("arraylen", "");
            Log.d("arraylen", String.valueOf(questions.length()));

            for (int i = 0; i < questions.length(); i++) { // Loop through questions
                Log.d("arraylen", String.valueOf(i));
                JSONObject questionObject = questions.getJSONObject(i);

                String category = questionObject.getString("category");
                Log.d("arraylen", category);

                String type = questionObject.getString("type");
                Log.d("arraylen", type);

                String difficulty = questionObject.getString("difficulty");
                Log.d("arraylen", difficulty);

                String question = questionObject.getString("question");
                Log.d("arraylen", question);

                String correct = questionObject.getString("correct_answer");
                Log.d("arraylen", correct);

                String incorrect = questionObject.getString("incorrect_answers");
                Log.d("arraylen", incorrect);
                Log.d("arraylen", "");

                Question questionClass = new Question(category, type, difficulty, question,
                        correct, incorrect);
                arrayList.add(questionClass);
            }

            activity.gotQuestion(arrayList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
