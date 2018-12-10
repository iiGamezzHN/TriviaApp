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
        void gotQuestion(ArrayList<Question> menus);
        void gotQuestionError(String message);
    }

    public QuestionsRequest(Context con) {
        context = con;
    }

    public void getQuestion(Callback activity) {
        String url = "https://opentdb.com/api.php?amount=10";
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

            for (int i = 0; i < questions.length(); i++) {
                JSONObject questionObject = questions.getJSONObject(i);


                String category = questionObject.getString("category");
                String type = questionObject.getString("type");
                String difficulty = questionObject.getString("difficulty");
                String question = questionObject.getString("question");
                String correct = questionObject.getString("correct_answer");
                String incorrect = questionObject.getString("incorrect_answers");
                String space = " ";

                Log.d("categories15", category);
                Log.d("categories15", type);
                Log.d("categories15", difficulty);
                Log.d("categories15", question);
                Log.d("categories15", correct);
                Log.d("categories15", incorrect);
                Log.d("categories15", space);
            }

            activity.gotQuestion(arrayList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
