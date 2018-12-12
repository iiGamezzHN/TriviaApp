package com.example.cpuga.triviaapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class HighscoresActivity extends AppCompatActivity implements Response.Listener<String>,
        Response.ErrorListener, HighscoreRequest.Callback {
    private int highscore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_highscores);

        if (getIntent().getSerializableExtra("scoreTag") == null) {

        } else {
            highscore = (int) getIntent().getSerializableExtra("scoreTag");
            post();
        }


        HighscoreRequest highscoreRequest = new HighscoreRequest(this);
        highscoreRequest.getHighscore(this);
    }

    public void gotHighscore(ArrayList arrayList) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                android.R.id.text1, arrayList);
        ListView listView = findViewById(R.id.highscore_list);
        listView.setAdapter(adapter);
    }

    public void post() {
        String url = "http://ide50-davidarisz.cs50.io:8080/list";
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        PostRequest request = new PostRequest(Request.Method.POST, url, this, this, highscore);
        queue.add(request);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }
}
