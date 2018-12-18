package com.example.cpuga.triviaapp;

import android.content.Context;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HighscoreRequest implements Response.Listener<JSONArray>, Response.ErrorListener {
    Context context;
    Callback activity;

    public interface Callback {
        void gotHighscore(ArrayList hs);
    }

    public HighscoreRequest(Context con) {
        context = con;
    }

    public void getHighscore(HighscoreRequest.Callback activity) {
        String url = "http://ide50-davidarisz.cs50.io:8080/list";
        RequestQueue queue = Volley.newRequestQueue(context);

        JsonArrayRequest jsonArray = new JsonArrayRequest(url, this, this);
        queue.add(jsonArray);

        this.activity = activity;
    }

    @Override
    public void onErrorResponse(VolleyError error) {
        if (error.getMessage() == null)
            Toast.makeText(context, "Timeout error :(", Toast.LENGTH_SHORT).show();
        else Toast.makeText(context, "Timeout error :(", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResponse(JSONArray response) {
        ArrayList<String> arrayList = new ArrayList();

        try {
            for (int i = 0; i < response.length(); i++) { // Loop though scores
                JSONObject object = response.getJSONObject(i);
                String name = object.getString("name");
                String score = object.getString("score");

                arrayList.add(score);
            }

            activity.gotHighscore(arrayList);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
